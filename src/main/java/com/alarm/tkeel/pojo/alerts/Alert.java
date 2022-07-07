package com.alarm.tkeel.pojo.alerts;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/24/9:53
 */
@Data
public class Alert implements Serializable {
    private Labels labels;
    private Annotations annotations;
    private String startsAt;
    private String endsAt;
    private String generatorURL;
    private String status;
    private String[] receivers;
    private String fingerprint;
}
