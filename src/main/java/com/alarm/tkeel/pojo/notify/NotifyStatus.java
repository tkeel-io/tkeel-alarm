package com.alarm.tkeel.pojo.notify;

import com.alarm.tkeel.pojo.rules.TenantParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/05/16:41
 */
@Data
public class NotifyStatus extends TenantParam implements Serializable {
    /**
     * 更新类型
     * 模板（temp）、设备（device）、遥测（telemetry）
     */
    private String type;
    private String objectId;
    /**
     * 0:未变更、1：删除、2：修改
     */
    private Integer status;
}
