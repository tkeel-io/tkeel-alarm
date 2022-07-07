package com.alarm.tkeel.pojo.notify;

import com.alarm.tkeel.pojo.rules.TenantParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/05/17:54
 */
@Data
public class DeviceStatus extends TenantParam implements Serializable {
    private List<String> deviceId;
    private Integer deviceStatus;
}
