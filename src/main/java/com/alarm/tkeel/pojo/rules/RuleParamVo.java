package com.alarm.tkeel.pojo.rules;

import com.alarm.tkeel.pojo.PageParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/28/22:11
 */
@Data
public class RuleParamVo extends PageParam implements Serializable {
    private Long ruleId;
    private String tenantId;
    // 规则名称
    private String ruleName;
    // 告警类型，0：基础告警；1：持续告警
    private Integer alarmType;
    //告警规则类型，0：阈值告警；1：系统告警
    private Integer alarmRuleType;
    // 告警级别，1，2,3,4; 1级最高，4级最低
    private Integer alarmLevel;
    // 告警源对象,0:平台;1:设备
    private Integer alarmSourceObject;

    private String promQl;
    // 告警描述
    private String ruleDesc;
    // 冗余字段，当告警源对象是 设备时生效。
    // 设备模板ID
    private String tempId;
    // 设备摸名称
    private String tempName;
    private String deviceId;
    private String deviceName;
    private String telemetryId;
    // 冗余字段，当告警源对象是 平台时生效
    private Map<String,String> platformAlarmRule;
    private List<PlatformRule> platformRuleList;
    //规则触发条件
    private List<DeviceCondition> deviceCondition;

    // 规则状态-是否启用，0：停用；1：启用
    private Integer enable;

    // 通知ID，为空则没有配置
    private String noticeId;

    //条件 “or”、“and”
    private String condition;


}
