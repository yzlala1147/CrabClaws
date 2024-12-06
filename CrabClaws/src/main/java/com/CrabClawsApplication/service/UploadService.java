package com.CrabClawsApplication.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {

    String uploadservice(MultipartFile file);

    String uploadservice_white(MultipartFile file);

}
