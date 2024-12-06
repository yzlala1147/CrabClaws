package com.CrabClawsApplication.service.impl;


import com.CrabClawsApplication.service.UploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    private static final String UPLOAD_DIR = "uploadfile"; // 上传文件保存的目录

    public String uploadservice(MultipartFile file){
        if (!file.isEmpty()) {
            try {
                // 创建上传文件保存目录（如果不存在）
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }
                // 获取原始文件名
                String originalFilename = file.getOriginalFilename();
                // 构建保存文件的路径
                Path destinationPath = Paths.get(uploadDir.getAbsolutePath(), originalFilename);
                // 将文件保存到指定路径
                Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                return "上传成功,文件路径保存在：/files/" + originalFilename;
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败";
            }
        } else {
            return "上传失败";
        }
    }
    public String uploadservice_white(MultipartFile file){
        if (!file.isEmpty()) {
            try {
                // 创建上传文件保存目录（如果不存在）
                File uploadDir = new File(UPLOAD_DIR);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 获取原始文件名
                String originalFilename = file.getOriginalFilename();
                int index = originalFilename.lastIndexOf("."); //获取文件后缀
                String suffix = originalFilename.substring(index).toLowerCase();  //拿到文件后缀名 并转换成小写
                if(suffix.equals(".jpg") ||suffix.equals(".png") ||suffix.equals(".gif")){
                    //重命名文件
                    String newFileName = UUID.randomUUID().toString() + suffix; //UUID是随机字符串+后缀
                    // 构建保存文件的路径
                    Path destinationPath = Paths.get(uploadDir.getAbsolutePath(), newFileName);
                    // 将文件保存到指定路径
                    Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    return "上传成功,文件路径保存在：/files/" + newFileName;
                }else {
                    return "文件后缀非法";
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败";
            }
        } else {
            return "上传失败";
        }
    }
}
