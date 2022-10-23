package com.alarm.tkeel.dao;


import com.alarm.tkeel.pojo.mail.*;

import java.util.List;

public interface MailMapper {
    int insertMail(Email mail);

    int updateMailConfig(Email mail);

    Email queryMailConfig();

    int createNoticeGroup(NoticeGroup noticeGroup);

//    int updateEmailAddress(NoticeGroup noticeGroup);

    int deleteNoticeGroup(NoticeGroup noticeGroup);

    List<NoticeGroup> queryNoticeGroupList(NoticeGroup noticeGroup);

    List<NoticeGroup> queryNoticeGroupByIds(List<Long> noticeIds);

    List<EmailAddressVo> queryEmailAddress(List<Long> noticeIds);

    List<EmailAddress> queryEmailAddressByNoticeId(Long noticeId);

    int createEmailAddress(EmailAddressVo emailAddressVo);

    int updateEmailAddress(EmailAddressVo emailAddressVo);

    List<NoticeCount> queryNoticeCount(String tenantId);
}
