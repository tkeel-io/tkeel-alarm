package com.alarm.tkeel.pojo;


import lombok.Data;

import java.io.Serializable;

@Data
public class Simulation implements Serializable {
    private Long alarmId;
    private Integer alarmLevel;
    private String tenantId;
    private String telemetryId;
    private String objectId;
    private String alarmSource;
    private Integer alarmStatus;
    private String alarmDesc;
    private String alarmValue;
    private Long startTime;
    private String userId;
    private String title;
}
