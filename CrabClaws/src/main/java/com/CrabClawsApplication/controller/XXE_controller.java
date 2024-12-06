package com.CrabClawsApplication.controller;



import com.CrabClawsApplication.pojo.Result;
import org.apache.tomcat.util.digester.Digester;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/XXE")
public class XXE_controller {

    @PostMapping("/DocumentBuilderFactory")
    public Result XXE_DocumentBuilderFactory(@RequestBody String XXE) {
        try {
            //XXE出发漏洞点
            DocumentBuilderFactory XXE_dbf = DocumentBuilderFactory.newInstance();
            //修复建议
//           XXE_dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
//           XXE_dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
//           XXE_dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

            DocumentBuilder XXE_db = XXE_dbf.newDocumentBuilder();
            //由于传入XML时会有URL编码，要先对URL进行解码
            String decodedString = URLDecoder.decode(XXE, "UTF-8");
            //String decodedString = "<?xml version=\"1.0\" ?><!DOCTYPE user [<!ENTITY admin SYSTEM \"file:///C:/windows/win.ini\"> ]><user><username>&admin;</username></user>";
            byte[] bytes = decodedString.getBytes(StandardCharsets.UTF_8);
            //转换成字节 在传入InputStream
            InputStream XXE_Poc = new java.io.ByteArrayInputStream(bytes);
            //传入XML内容 并执行了恶意代码
            Document XXE_d = XXE_db.parse(XXE_Poc);

            String username = XXE_d.getElementsByTagName("username").item(0).getTextContent();
            if(username.equals("admin")){
                return Result.success("用户\"" + username + "\"存在");
            }else {
                return Result.success("用户\"" + username + "\"不存在");
            }
        } catch (Exception e) {
          return  Result.error(e.getMessage());
        }
    }

    @PostMapping("/XMLReader")
    public Result XXE_XMLReader(@RequestBody String XXE) {
        try {
            //可以通过 XXE打SSRF
            //String decodedString = "<?xml version=\"1.0\" ?><!DOCTYPE user [<!ENTITY aaa SYSTEM \"http://dnslog/\"> ]><comment><text>&aaa;</text></comment>"
            String decodedString = URLDecoder.decode(XXE, "UTF-8");
            //XMLReader 漏洞触发点
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            InputSource inputSource = new InputSource(new StringReader(decodedString));
            /* 修复建议：
            xmlReader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            xmlReader.setFeature("http://xml.org/sax/features/external-general-entities", false);
            xmlReader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            */
            //解析恶意语句
            xmlReader.parse(inputSource);
            return Result.success();
        } catch (Exception e) {
            return  Result.error(e.getMessage());
        }
    }
    @PostMapping("/Digester")
    public Result XXE_Digester(@RequestBody String XXE) {
        try {
            //可以通过 XXE打SSRF
            //String decodedString = "<?xml version=\"1.0\" ?><!DOCTYPE user [<!ENTITY aaa SYSTEM \"http://dnslog/\"> ]><comment><text>&aaa;</text></comment>"
            String decodedString = URLDecoder.decode(XXE, "UTF-8");

            Digester digester = new Digester();
            InputSource inputSource = new InputSource(new StringReader(decodedString));
            digester.parse(inputSource);
            return Result.success();
        } catch (Exception e) {
            return  Result.error(e.getMessage());
        }
    }


}
