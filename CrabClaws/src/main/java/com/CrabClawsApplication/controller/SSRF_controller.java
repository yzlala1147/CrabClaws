package com.CrabClawsApplication.controller;

import com.CrabClawsApplication.pojo.SSRF_No;
import com.CrabClawsApplication.service.SsrfService;
import com.CrabClawsApplication.Security.WebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.CrabClawsApplication.config.IPconfig.URL;


@RestController
@CrossOrigin
@RequestMapping("/SSRF")
public class SSRF_controller {

    @Autowired
    private SsrfService ssrfService;

    @GetMapping("/GetImage")
    public ResponseEntity<byte[]> GetImage(@RequestParam("Image") String image) {
        String[] http = {"http://","https://"};
        //判断是否是网页请求
        if (!WebSecurity.RFS(http,image)){
            image = URL + "api/files/" + image;
        }
        return ssrfService.fetchImage(image);
    }

    @GetMapping("/GetImage_No")
    public ResponseEntity<byte[]> GetImage_No(@RequestParam("Image") int num) {
        String image404 = URL + "api/files/404.png";
        //通过数据库获取图片路径
        SSRF_No SSRF = ssrfService.SSRFNO(num);
        if(SSRF != null) {
            return ssrfService.fetchImage(URL + SSRF.getPath());
        }else {
            return ssrfService.fetchImage(image404);
        }

    }
}

