package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/30/18:26
 */
@Data
public class UpdateNoticeParamVo extends TenantParam implements Serializable {
    private Long ruleId;
    private String noticeId;
}
