package com.alarm.tkeel.pojo.mail;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/30/16:09
 */
@Data
@ApiModel(description = "邮件服务器配置类")
public class Email implements Serializable {
    private String id;
    private String smtpAddress;
    private String port;
    // 0:不启用；1:启用，默认 0
    private int ssl;
    private String smtpUserName;
    private String smtpPassWord;
    private String fromAddress;
    /**
     * 收件人地址，用于邮件服务器测试
     */
    private String to;
}
