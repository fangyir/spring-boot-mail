package com.example.helloWorld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * author fangyr
 * date 2018/10/10 18:42
 */
@Service
public class MailService {
    public void sayHello(){
        System.out.println("Hello World");
    }
    //注入配置文件中的属性
    @Value("${spring.mail.username}")
    private String from;//发件人
    @Autowired
    private JavaMailSender mailSender;
    /**
     * 发送简单文本邮件
     * */
    public void sendSimpleMail(String to, String subject, String content){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//发送给谁
        message.setSubject(subject);//发送主题
        message.setText(content);//发送内容
        message.setFrom(from);
        try {
            mailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 发送HTML邮件
     * */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(message);
    }
    /**
     * 发送带附件的邮件
     * */
    public void sendAttachmentsMail(String to,String subject,String content,String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName,file);
        helper.addAttachment(fileName + "_test",file);//增加一个叫test的附件
        mailSender.send(message);
    }
    /**
     * 发送带图片的邮件
     * */
    public void sendInlinSourceMail(String to,String subject,String content,String rscPath,String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId,res);
        mailSender.send(message);
    }
}
