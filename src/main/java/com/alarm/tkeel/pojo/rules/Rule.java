package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/30/14:03
 */
@Data
public class Rule implements Serializable {
    private Long ruleId;
    private String tenantId;
    //规则名称
    private String ruleName;
    // 告警类型，0：基础告警；1：持续告警
    private Integer alarmType;
    //告警规则类型，0：阈值告警；1：系统告警
    private Integer alarmRuleType;
    // 告警级别，1，2,3,4; 1级最高，4级最低
    private Integer alarmLevel;
    private Integer alarmSourceObject;
    // 设备模板ID
    private String tempId;
    // 设备摸名称
    private String tempName;
    private String deviceId;
    private String deviceName;
    private String telemetryId;
    private String promQl;
    // 告警描述
    private String ruleDesc;
    // 通知ID，为空则没有配置
    private String noticeId;
    // 规则状态-是否启用，0：停用；1：启用
    private int enable;

    private int deleted;

    //条件 “or”、“and”
    private String condition;

    // 设备模板、设备、遥测变更状态字段
    private Integer tempStatus;
    private Integer deviceStatus;
    private Integer telemetryStatus;
    //创建时间
    private Date createTime;
}
