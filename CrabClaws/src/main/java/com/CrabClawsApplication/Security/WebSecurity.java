package com.CrabClawsApplication.Security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebSecurity {
    static public boolean RFS(String dangerous, String filenname){
        Pattern pattern = Pattern.compile(dangerous);
        Matcher matcher = pattern.matcher(filenname);

        //正则表达匹配字符
        while (matcher.find()){
            if(matcher.group() != null){
                return true;
            }
        }
        return false;
    }

    static public boolean RFS(String[] dangerous, String filenname) {
        // 循环遍历敏感字符数组
        for (String danger : dangerous) {
            Pattern pattern = Pattern.compile(danger);
            Matcher matcher = pattern.matcher(filenname);

            //正则表达匹配字符
            while (matcher.find()) {
                if (matcher.group()!= null) {
                    return true;
                }
            }
        }
        return false;
    }
        static public boolean suffix_Security(String filename) {
            if (filename.lastIndexOf(".") >= 0) {
                int index = filename.lastIndexOf("."); //获取文件后缀
                String suffix = filename.substring(index).toLowerCase();  //拿到文件后缀名 并转换成小写
                //限制读取文件的后缀
                if (suffix.equals(".jpg") || suffix.equals(".png") || suffix.equals(".gif") || suffix.equals(".txt")) {
                    return false;
                }
            }return true;
        }
}
