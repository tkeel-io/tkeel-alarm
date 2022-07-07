package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/10/15:48
 */
@Data
public class Desc implements Serializable {
    private Long ruleId;
    private String tenantId;
    private String telemetryId;
    private String telemetryName;
    private Integer telemetryType;
    // 0:平台，1:设备
    private Integer alarmSourceObject;
    private Integer time;
    private String polymerize;
    private String operator;
    private String condition;
    private String label;
    private String value;
    private String platRuleId;
    private Integer deleted;

    //遥测变更状态字段
    private Integer telemetryStatus;
}
