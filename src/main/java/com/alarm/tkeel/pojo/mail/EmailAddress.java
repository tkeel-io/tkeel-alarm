package com.alarm.tkeel.pojo.mail;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/01/14:58
 */
@Data
public class EmailAddress implements Serializable {
    private Long id;
    private Long noticeId;
    private String userName;
    private String emailAddress;
}
