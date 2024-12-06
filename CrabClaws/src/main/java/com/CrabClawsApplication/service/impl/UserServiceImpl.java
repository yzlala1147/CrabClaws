package com.CrabClawsApplication.service.impl;


import com.CrabClawsApplication.mapper.UserMapper;
import com.CrabClawsApplication.pojo.User;
import com.CrabClawsApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.getByUsernameAndPassword(user);
    }

    @Override
    public User SQL_injection(String id) {
        return userMapper.SQL_injection(id);
    }
    @Override
    public User SQL_injection_No(int id) {
        return userMapper.SQL_injection_No(id);
    }

    @Override
    public boolean judgment_pwd(User user){
        if(userMapper.judgment_pwd(user) != null){
            return true;
        }else return false;
    }

    @Override
    public void Repwd(User user){
        userMapper.Repwd(user);
    }


    @Override
    public User getUserByName(String username) {
       return userMapper.getUserByName(username);
    }


}
