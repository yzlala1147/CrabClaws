package com.CrabClawsApplication.service;

import com.CrabClawsApplication.pojo.SSRF_No;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface SsrfService {

    ResponseEntity<byte[]> fetchImage(String image);
    SSRF_No SSRFNO(int imagenumber);
}
