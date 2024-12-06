package com.CrabClawsApplication.service.impl;

import com.CrabClawsApplication.mapper.SSRF_No_Mapper;
import com.CrabClawsApplication.pojo.SSRF_No;
import com.CrabClawsApplication.service.SsrfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class SsrfServiceImpl implements SsrfService{
    @Autowired
    private SSRF_No_Mapper SSRF_No_Mapper;
    public ResponseEntity<byte[]> fetchImage(String image){
        try {

            URL url = new URL(image);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer))!= -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                inputStream.close();

                byte[] imageData = outputStream.toByteArray();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG); // 根据实际图片类型设置

                return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public SSRF_No SSRFNO(int imagenumber) {return SSRF_No_Mapper.SSRF_No(imagenumber);}
}
