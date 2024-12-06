package com.CrabClawsApplication.mapper;

import com.CrabClawsApplication.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username} and password = #{password};")
    User getByUsernameAndPassword(User user);

    @Select("select * from user where ID = '${ID}'")
    User SQL_injection(String ID);

    @Select("select * from user where ID = #{ID}")
    User SQL_injection_No(int ID);

    @Select("select * from user where username = #{username} and password = #{password}")
    User judgment_pwd(User user);

    @Update("update user set password = (#{password}) where username = (#{username})")
    void Repwd(User user);

    @Select("select * from user where username = '${username}'")
    User getUserByName(String username);
}
