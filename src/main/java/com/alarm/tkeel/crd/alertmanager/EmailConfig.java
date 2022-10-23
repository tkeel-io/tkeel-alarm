package com.alarm.tkeel.crd.alertmanager;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/22/14:10
 */
@Data
public class EmailConfig implements Serializable {
    private String to;
    private boolean send_resolved;

}
