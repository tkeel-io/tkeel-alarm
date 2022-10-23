package com.alarm.tkeel.pojo.mail;

import com.alarm.tkeel.pojo.PageParam;
import com.alarm.tkeel.pojo.rules.TenantParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/01/14:52
 */
@Data
public class NoticeGroup extends PageParam implements Serializable {
    private Long noticeId;
    private String tenantId;
    private String groupName;
    private String noticeDesc;
    private String emailAddress;
    // 是否删除，1:删除
    private Integer deleted;
}
