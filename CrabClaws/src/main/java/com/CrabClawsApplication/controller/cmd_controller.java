package com.CrabClawsApplication.controller;

import com.CrabClawsApplication.pojo.Result;
import com.CrabClawsApplication.service.CmdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/cmd")
public class cmd_controller{
    @Autowired
    private CmdService cmdService;

    @PostMapping("/cmd1")
    public Result execCommand(@RequestParam("cmd") String cmd) {
        return cmdService.execCommand(cmd);
    }
    @PostMapping("/cmd2")
    public Result ProcessBuilderCommand(@RequestParam("cmd") String cmd) {
        return cmdService.ProcessBuilderCommand(cmd);
    }
    /*
    修复建议：cmd参数最好不可控。
     */
}



