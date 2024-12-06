package com.CrabClawsApplication.controller.login;

import com.CrabClawsApplication.Security.JwtUtils;
import com.CrabClawsApplication.pojo.Result;
import com.CrabClawsApplication.pojo.User;
import com.CrabClawsApplication.service.UserService;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@CrossOrigin
@RestController
@Mapper
public class login_controller {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        log.info("用户登录：{}",user);
        User u = userService.login(user);
        if(u != null){
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", u.getId());
            claims.put("name", u.getName());
            claims.put("username", u.getUsername());
            String jwt = JwtUtils.generateJwt(claims);
            return Result.success(jwt);
        }
        return Result.error("用户名或密码错误");
    }

    public String userinfo(String jwt){
        //获取用户名
        String username = (String)Jwts.parser().setSigningKey(JwtUtils
                                                .getSignKey())
                                                .parseClaimsJws(jwt)
                                                .getBody()
                                                .get("username");
        return username;
    }


    @PostMapping("/repwd")
    Result repwd(@RequestParam("oldpassword") String oldpwd, @RequestParam("newpassword") String newpwd, HttpServletRequest request){
        User user = new User();
        String jwt = request.getHeader("token");
        String username = userinfo(jwt);
        user.setUsername(username); user.setPassword(oldpwd);
        //判断用户密码是否正确
        if(userService.judgment_pwd(user)){
            user.setPassword(newpwd);
            userService.Repwd(user);
            return Result.success("true");
        }else return Result.error("false");
    }
}
