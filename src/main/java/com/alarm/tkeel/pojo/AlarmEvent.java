package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author neov
 * @Date 2022/10/23/14:45
 */
@Data
public class AlarmEvent implements Serializable {
    private Long eventId;
    private Long alarmId;
    private String recordHash;
    private Long ruleId;
    private String tenantId;
    private String alarmName;
    private Integer alarmLevel;
    private Integer alarmSource;
    private Integer alarmType;
    private Integer alarmStrategy;
    private String objectId;
    private String telemetryId;
    private Long startTime;
    private Long handTime;
    private Integer alarmStatus;
    private String handOpinions;
    private String alarmValue;
    private String alarmDesc;
    private Integer deleted;

}