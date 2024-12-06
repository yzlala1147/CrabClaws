package com.CrabClawsApplication.service.impl;

import com.CrabClawsApplication.pojo.Result;
import java.io.*;
import java.nio.charset.Charset;
import com.CrabClawsApplication.service.CmdService;
import org.springframework.stereotype.Service;

@Service
public class CmdServiceImpl implements CmdService{
    public Result execCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec("cmd.exe /c" + command);
            // 获取命令执行的输出流
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("GBK")));

            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine())!= null) {
                output.append(line).append("\n");
            }

            // 等待命令执行完成并获取退出码
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return Result.success(output.toString());
            } else {
                return Result.error("命令执行错误" + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return Result.error("命令执行错误");
    }
    public Result ProcessBuilderCommand(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            // 根据操作系统设置要执行的具体命令
            if (System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                processBuilder.command("cmd", "/c", command);
            } else if (System.getProperty("os.name").toLowerCase().startsWith("linux")) {
                processBuilder.command(command);
            } else {
                return Result.error("不支持的操作系统");
            }
            // 设置工作目录
            processBuilder.directory(new File("C:\\"));

            Process process = processBuilder.start();
            Charset charset = Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("windows")? "GBK" : "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(),charset));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine())!= null) {
                output.append(line).append("\n");
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return Result.success(output.toString());
            } else {
                return Result.error("命令执行失败，退出码: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Result.error("命令执行过程中出现错误");
        }
    }
}