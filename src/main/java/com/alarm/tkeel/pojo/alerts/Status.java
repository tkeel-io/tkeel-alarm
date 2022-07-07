package com.alarm.tkeel.pojo.alerts;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/24/9:51
 */
@Data
public class Status implements Serializable {
    private String state;
    private String silencedBy;
    private String inhibitedBy;
}
