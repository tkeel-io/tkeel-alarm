package com.alarm.tkeel.pojo;

import lombok.Data;
import java.io.Serializable;

/**
 * @Author guojun
 * @Description 告警实体
 * @Date 2022/05/18/15:24
 */
@Data
public class PortalAlarm implements Serializable {
    private String alarmId;
    private String objectId;
    private String alarmEventId;
    private Integer alarmLevel;
    private String tenantId;
    private String telemetryId;
    private Integer alarmStatus;
    private String alarmDesc;
//    private String alarmValue;
    private Long startTime;
}
