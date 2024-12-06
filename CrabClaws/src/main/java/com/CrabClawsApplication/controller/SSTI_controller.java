package com.CrabClawsApplication.controller;

import com.CrabClawsApplication.pojo.Result;
import org.springframework.web.bind.annotation.*;
import org.apache.velocity.*;
import org.apache.velocity.app.Velocity;

import java.io.*;

@RestController
@CrossOrigin
public class SSTI_controller {

    @PostMapping("/SSTI")
    public Result SpEL(@RequestParam("payload") String payload) {
            Velocity.init();
            VelocityContext context = new VelocityContext();
            StringWriter stringWriter = new StringWriter();
            try {
                Velocity.evaluate(context,stringWriter,"SpEl",payload);
                return Result.success("成功");
            } catch (Exception e) {
                System.out.println(new RuntimeException(e));
                return Result.error("失败");
            }
    }
}
