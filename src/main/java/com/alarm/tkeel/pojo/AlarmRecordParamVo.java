package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/25/18:22
 */
@Data
public class AlarmRecordParamVo extends PageParam implements Serializable {
    private String alarmId;
    private String tenantId;
    private Integer alarmStatus;
    private Integer alarmType;
    private Integer alarmLevel;
    private Integer alarmSource;
    private Integer alarmStrategy;
    private Long startTime;
    private Long endTime;
}
