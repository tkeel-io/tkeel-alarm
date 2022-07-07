package com.alarm.tkeel.service;


import com.alarm.tkeel.pojo.mail.Email;
import com.alarm.tkeel.pojo.mail.EmailAddress;
import com.alarm.tkeel.pojo.mail.NoticeGroup;
import com.alarm.tkeel.utils.PageInfo;

import java.util.List;


public interface MailService {
    int insertMail(Email email);

//    int UpdateMailConfig(Email email);

    Email queryMailConfig();

    int createNoticeGroup(NoticeGroup noticeGroup);

    int updateEmailAddress(NoticeGroup noticeGroup);

    int deleteNoticeGroup(NoticeGroup noticeGroup);

    PageInfo<NoticeGroup> queryNoticeGroupList(NoticeGroup noticeGroup);

    List<NoticeGroup> queryNoticeGroupByIds(List<Long> noticeIds);
}
