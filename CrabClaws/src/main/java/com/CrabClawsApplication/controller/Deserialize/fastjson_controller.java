package com.CrabClawsApplication.controller.Deserialize;

import com.CrabClawsApplication.pojo.Result;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/serialize")
public class fastjson_controller {
    static {
        //开启autoType
        System.setProperty("fastjson.parser.autoTypeSupport", "true");
    }

    @PostMapping("/fastjson")
    public Result fastjson_vul(@RequestBody String content) {
        try {
            String fastjosn = JSONObject.parseObject(content).toString();
            return Result.success(fastjosn);
       } catch (Exception e) {
           return Result.error(e.toString());
       }
    }
   //升级fastjson版本   <version>1.2.83</version>
}
