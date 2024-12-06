package com.CrabClawsApplication.controller;

import com.CrabClawsApplication.service.UploadService;
import com.CrabClawsApplication.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@CrossOrigin
@Controller
@Mapper
public class upload_controller {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public Result uploadFile(@RequestParam("file") MultipartFile file) {
        String filepath = uploadService.uploadservice(file);
        return Result.success(filepath);
    }
    @PostMapping("/upload_white")
    public Result uploadFile_white(@RequestParam("file") MultipartFile file) {
        String filepath = uploadService.uploadservice_white(file);
        return Result.success(filepath);
    }
}

