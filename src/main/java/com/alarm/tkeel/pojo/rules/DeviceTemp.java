package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/28/14:59
 */
@Data
public class DeviceTemp implements Serializable {
    //模板ID
    private String tempId;
    //遥测ID
    private String telemetryId;
}
