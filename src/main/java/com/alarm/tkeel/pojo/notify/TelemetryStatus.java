package com.alarm.tkeel.pojo.notify;

import com.alarm.tkeel.pojo.rules.TenantParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/05/17:55
 */
@Data
public class TelemetryStatus extends TenantParam implements Serializable {
    private List<Long> ruleId;
    private List<String> telemetryId;
    private Integer telemetryStatus;
}

