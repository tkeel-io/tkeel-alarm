package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/19/16:54
 */
@Data
public class UpdateStatusParam implements Serializable {
    private String alarmEventId;
    private String tenantId;
    private Integer alarmStatus;
    private Long handTime;
}
