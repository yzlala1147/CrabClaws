package com.CrabClawsApplication.controller.Deserialize;

import com.CrabClawsApplication.pojo.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;

import java.util.Base64;

@CrossOrigin
@RestController
public class shiro_controller {

    byte[] newKey = new byte[16];
    SecureRandom secureRandom = new SecureRandom();

    //生成shiro Key
    public String getSecureRandom() {
        secureRandom.nextBytes(newKey);
        String newKeyBase64 = Base64.getEncoder().encodeToString(newKey);
        return newKeyBase64;
    }

    @GetMapping("/GetShiroKey")
    @ResponseBody
    public Result getShiroKey(){
        //shiro的key在ShiroConfig中
        return Result.success("Shiro的key为：" + "823Cvv7bHVZlebvEnlZWfQ==");
    }
}
