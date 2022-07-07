package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/19/15:09
 */
@Data
public class PortalAlarmParamVo implements Serializable {
    private String alarmId;
    private String tenantId;
    private Integer[] alarmStatus;
    private String telemetryId;
    private Integer resultCount;
}
