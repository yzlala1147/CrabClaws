package com.CrabClawsApplication.controller.Deserialize;

import com.CrabClawsApplication.pojo.Result;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@CrossOrigin
@RestController
@RequestMapping("/serialize")
public class log4j2_controller {
    private static final Logger logger = LogManager.getLogger();

    @PostMapping("/log4j2")
    public Result log4j2(@RequestParam("payload") String payload){
        logger.error(payload);
        return Result.success(payload);
    }
    //
}
