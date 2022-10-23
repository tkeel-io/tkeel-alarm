package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/01/11:21
 */
@Data
public class CheckBind implements Serializable {
    private String tempId;
    private String deviceId;
    private String telemetryId;
    /**
     * 0：未变更(默认)，1：删除，2：修改
     */
    private Integer status;
}
