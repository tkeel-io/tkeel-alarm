package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/28/22:13
 */
@Data
public class DeviceCondition implements Serializable {
    // 模板ID
    private String tempId;
    // 遥测ID
    private String telemetryId;
    private String telemetryName;
    //遥测类型，0:枚举；1:布尔；2:普通(聚合比较)
    private Integer telemetryType;
    // 时间,0:及时;1:1分钟;3:3分钟;5:5分钟
    private Integer time;
    // 聚合,avg（平均值）；max（最大值）；min（最小值）
    private String polymerize;
    // 运算符
    private String operator;
    private String condition;
    private String label;
    private String value;
}
