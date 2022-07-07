package com.alarm.tkeel.pojo.mail;

import com.alarm.tkeel.pojo.rules.TenantParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/05/14:02
 */
@Data
public class BindingQuery extends TenantParam implements Serializable {
    private String noticeId;
}
