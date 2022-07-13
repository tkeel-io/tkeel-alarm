package com.alarm.tkeel.utils;

import com.alarm.tkeel.pojo.mail.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/08/14:09
 */
@Service
public class EmailSendUtils {
    // 邮件发送类
    @Autowired
    private JavaMailSender mailSender;
    // 简单邮件信息类


    /**
     * @return : void
     * @Description : 发送普通文本邮件
     */
    public  int sendWithText(Email email) {
        try {
            mailSender = new JavaMailSenderImpl();
            String host = email.getSmtpAddress();
            String userName = email.getSmtpUserName();
            String password =email.getSmtpPassWord();
            String port = email.getPort();
            // 设置邮件服务主机
            ((JavaMailSenderImpl) mailSender).setHost(host);
            ((JavaMailSenderImpl) mailSender).setUsername(userName);
            ((JavaMailSenderImpl) mailSender).setPassword(password);
            Properties pro = new Properties();
            if(email.getSsl() > 0) {
                pro.put("mail.smtp.ssl.enable", "true");
                pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            }else {
                pro.put("mail.smtp.ssl.enable", "false");
            }
            pro.put("mail.smtp.auth", "true");
            pro.put("mail.smtp.port", port);
            pro.put("mail.smtp.socketFactory.port", port);
            pro.put("mail.smtp.socketFactory.fallback", "false");
            ((JavaMailSenderImpl) mailSender).setJavaMailProperties(pro);


            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(email.getFromAddress());
            simpleMailMessage.setTo(email.getTo());
            simpleMailMessage.setSubject("tkeel平台监控告警测试邮件");
            simpleMailMessage.setText("tkeel-alarm");
            simpleMailMessage.setSentDate(new Date());
            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            System.out.println("邮件服务器初始化错误!"+e.getMessage());
            return 0;
        }
        return 1;
    }

}
