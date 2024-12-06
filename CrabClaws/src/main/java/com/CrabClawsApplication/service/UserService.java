package com.CrabClawsApplication.service;

import com.CrabClawsApplication.pojo.User;
import org.springframework.stereotype.Service;


@Service
public interface UserService {

    User login(User user);

    User SQL_injection(String id);

    User SQL_injection_No(int id);

    boolean judgment_pwd(User user);
    void Repwd(User user);


    User getUserByName(String username);
}
