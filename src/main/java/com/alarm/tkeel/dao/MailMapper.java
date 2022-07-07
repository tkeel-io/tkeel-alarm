package com.alarm.tkeel.dao;


import com.alarm.tkeel.pojo.mail.Email;
import com.alarm.tkeel.pojo.mail.EmailAddress;
import com.alarm.tkeel.pojo.mail.NoticeGroup;

import java.util.List;

public interface MailMapper {
    int insertMail(Email mail);

    int updateMailConfig(Email mail);

    Email queryMailConfig();

    int createNoticeGroup(NoticeGroup noticeGroup);

    int updateEmailAddress(NoticeGroup noticeGroup);

    int deleteNoticeGroup(NoticeGroup noticeGroup);

    List<NoticeGroup> queryNoticeGroupList(NoticeGroup noticeGroup);

    List<NoticeGroup> queryNoticeGroupByIds(List<Long> noticeIds);
}
