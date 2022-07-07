package com.alarm.tkeel.pojo.alerts;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/24/9:45
 */
@Data
public class Labels implements Serializable {
    private String alertname;
    private Long ruleId;
    private String instance;
    private String job;
    private String model;
    private String status;
    private String team;
//    private String deviceId;
    private String tenantId;
    private String telemetry_id;
    private String objectId;
    private Integer alarmType;
    private Integer alarmStrategy;
    private Integer alarmSource;
    private Integer alarmLevel;
    private String alarmValue;
}
