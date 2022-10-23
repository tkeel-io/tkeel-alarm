package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/19/14:45
 */
@Data
public class AlarmRecord implements Serializable {
    private Long alarmId;
    private Long ruleId;
//    private String alarmEventId;
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
    private String alarmDesc;
    private String alarmValue;
    private Integer deleted;

}
