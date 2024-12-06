package com.CrabClawsApplication.controller;

import com.CrabClawsApplication.pojo.Result;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CrabClawsApplication.Security.WebSecurity;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@Mapper
@RequestMapping("/files")
public class files_controller {
    private static final String UPLOAD_DIR = "image";

    @GetMapping("/fileread")
    public Result readFile(@RequestParam("filename") String filename) {
        try {
            // 假设文件在类路径下，这里使用ClassPathResource来获取文件资源
            String filePath = UPLOAD_DIR + "/" + filename;
            Resource resource = new FileSystemResource(filePath);
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null) {
                content.append(line).append("\n");
            }
            reader.close();
            inputStream.close();
            // 返回包含文件内容的响应实体
            return Result.success(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            // 如果读取文件出现错误，返回错误状态码和错误信息
            return Result.error("读取文件失败");
        }
    }
    //通过过滤../ 可以阻值任意文件读取
    @GetMapping("/fileread_no")
    public Result readFile_no(@RequestParam("filename") String filename) {
        try {

            if (WebSecurity.RFS("\\.\\./",filename)){
                return Result.error("路径非法");
            }
            if(WebSecurity.suffix_Security(filename)){
                return Result.error("文件后缀非法");
            }
            // 假设文件在类路径下，这里使用ClassPathResource来获取文件资源
            String filePath = UPLOAD_DIR + "/" + filename;
            Resource resource = new FileSystemResource(filePath);
            InputStream inputStream = resource.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null) {
                content.append(line).append("\n");
            }
            reader.close();
            inputStream.close();
            // 返回包含文件内容的响应实体
            return Result.success(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            // 如果读取文件出现错误，返回错误状态码和错误信息
            return Result.error("读取文件失败");
        }
    }


//只能读取uploadfile路径下的文件 文件夹里的不行
    @GetMapping("/{filename}")
    public ResponseEntity<FileSystemResource> File(@PathVariable("filename") String filename) {
        FileSystemResource file = new FileSystemResource(new File(UPLOAD_DIR, filename));

        if (file.exists()) {
            try {
                Path filePath = Paths.get(UPLOAD_DIR, filename);
                String contentType = Files.probeContentType(filePath);

                HttpHeaders headers = new HttpHeaders();
                if (contentType!= null) {
                    headers.setContentType(MediaType.parseMediaType(contentType));
                }

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(file);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
