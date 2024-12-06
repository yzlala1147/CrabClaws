package com.CrabClawsApplication.controller;


import com.CrabClawsApplication.pojo.User;
import com.CrabClawsApplication.pojo.Result;
import com.CrabClawsApplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@CrossOrigin
@RestController
@Mapper
public class sql_injection_controller {
    @Autowired
    private UserService userService;
    //报错注入
    @GetMapping(value="/SQL1")
    public User SQL_boolean(@RequestParam("id") String id){
        log.info("SQL注入参数：{}",id);

        User userid = null;
        try {
            userid = userService.SQL_injection(id);
        } catch (Exception e) {
            if (e instanceof org.springframework.dao.DataAccessException) {
                org.springframework.dao.DataAccessException dae = (org.springframework.dao.DataAccessException) e;
                return new User(null,  dae.getMessage(), null, null);
            }
        }
        return userid;
    }

    //盲注
    @GetMapping(value="/SQL2")
    public Result SQL_blind(@RequestParam("id") String id){
        log.info("SQL注入参数：{}",id);
        try {
        String TandF = "false";
        User user = userService.SQL_injection(id);
        if(user != null){
           TandF = "true";
       }
       return Result.success(TandF);
        } catch (Exception e) {

        }
        return Result.error("SQL语句出错");
    }


    //延时盲注
    @PostMapping(value="/SQL3")
    public Result SQL_sleep(@RequestParam("id") String id){
        log.info("SQL注入参数：{}",id);
        try {
        User userid =  userService.SQL_injection(id);
        return Result.success();
        } catch (Exception e) {

        }
        return Result.error("SQL语句出错");
    }

    @GetMapping(value="/SQL_No")
    public User SQL_boolean_No(@RequestParam("id") int id){
        log.info("SQL参数：{}",id);

        User userid = null;
        try {
            userid = userService.SQL_injection_No(id);
        } catch (Exception e) {
            if (e instanceof org.springframework.dao.DataAccessException) {
                org.springframework.dao.DataAccessException dae = (org.springframework.dao.DataAccessException) e;
                return new User(null,  dae.getMessage(), null, null);
            }
        }
        return userid;
    }



}
