package com.CrabClawsApplication.controller.login;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RestController
@Mapper
public class Shrio_login_controller {
    @GetMapping("/logins")//使用get方式，方便参数传递
    public String login( String name, String password){
        Subject subject= SecurityUtils.getSubject();
        //通过name,password封装为token对象中
        //现在能理解我们自定义的登录验证方法中，为什么能够从token中获取用户信息了吧
        AuthenticationToken token=new UsernamePasswordToken(name,password);
        System.out.println(token);
        //然后调用subject.login方法登录
        try {
            //此时就会去执行我们自定义的登录验证的方法，同时将token作为参数传递过去了
            subject.login(token);
            return "登录成功";
        }catch (AuthenticationException e){
            e.printStackTrace();
            return "登录失败！";
        }
    }
}

