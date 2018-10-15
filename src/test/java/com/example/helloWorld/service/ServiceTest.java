package com.example.helloWorld.service;

import com.example.helloWorld.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;

/**
 * author fangyr
 * date 2018/10/10 18:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Resource
    MailService mailService;
    @Test
    public void sayHelloTest(){
        mailService.sayHello();
    }
    @Test
    /**
     * 发送简单文本邮件测试方法
     * */
    public void sendSimpleMailTest(){
        mailService.sendSimpleMail("2441430630@qq.com","第一封邮件","大家好，这是我的第一封邮件");
    }
    /**
     * 发送HTML邮件
     * */
    @Test
    public void sendHtmlMailTest() throws MessagingException {
        String content = "<html>\n" +
                "<body>\n" +
                "<h3> hello world,这是一封Html邮件!<h3>\n" +
                "</body>\n"+
                "</html>";
        mailService.sendHtmlMail("2441430630@qq.com","这是一封Html邮件",content);
    }
    /**
     * 发送带附件的邮件
     * */
    @Test
    public void sendAttachmentsMail() throws MessagingException {
        String filePath = "/example/helloWorld.zip";
        mailService.sendAttachmentsMail("2441430630@qq.com","这是一封带附件的邮件","这是一篇带附件的邮件内容",filePath);
    }
}
