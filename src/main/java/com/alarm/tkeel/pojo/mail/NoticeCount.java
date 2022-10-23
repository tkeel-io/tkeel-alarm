package com.alarm.tkeel.pojo.mail;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/12/11:13
 */
@Data
public class NoticeCount implements Serializable {
    private Long noticeId;
    private Integer count;

}
