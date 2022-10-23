package com.alarm.tkeel.pojo.notify;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/05/16:08
 */
@Data
public class Notification implements Serializable {
    private String id;
    private String title;
    private String content;
    private Long create_timestamp;
    private Action action;
}
