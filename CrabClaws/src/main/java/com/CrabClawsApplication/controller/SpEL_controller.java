package com.CrabClawsApplication.controller;

import com.CrabClawsApplication.pojo.Result;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.nio.charset.Charset;


@CrossOrigin
@RestController
public class SpEL_controller {

    @PostMapping("/SpEL")
    public Result SpEL(@RequestParam("payload") String payload) {
        try {
            ExpressionParser expressionParser = new SpelExpressionParser();
                                        //将恶意代码保存在expression
            Expression expression = expressionParser.parseExpression(payload);
                                        //expression.getValue() 执行恶意代码
            Process process = (Process) expression.getValue();

            Charset charset = Charset.forName(System.getProperty("os.name").toLowerCase().startsWith("windows") ? "GBK" : "UTF-8");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), charset));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
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
