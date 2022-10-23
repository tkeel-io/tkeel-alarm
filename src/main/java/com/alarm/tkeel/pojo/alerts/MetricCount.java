package com.alarm.tkeel.pojo.alerts;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/04/11:15
 */
@Data
public class MetricCount implements Serializable {
    private String tenantId;
    private Integer count;
    private String alarmLevel;
}
