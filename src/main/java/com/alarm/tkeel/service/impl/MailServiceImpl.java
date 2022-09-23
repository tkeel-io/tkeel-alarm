package com.alarm.tkeel.service.impl;

import com.alarm.tkeel.dao.MailMapper;
import com.alarm.tkeel.pojo.mail.*;
import com.alarm.tkeel.service.MailService;
import com.alarm.tkeel.utils.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;


@Service
public class MailServiceImpl implements MailService {

    @Resource
    private MailMapper mailMapper;
    @Autowired
    private RuleServiceImpl ruleService;



    @Override
    public int insertMail(Email email) {
        if(email.getId() == null || email.getId().equals("")) {
            email.setId(UUID.randomUUID().toString());
            return mailMapper.insertMail(email);
        }else {
            try {
                int code = mailMapper.updateMailConfig(email);
                int config = ruleService.setAlertManagerSecret();
                if(config == 0){
                    System.out.println("更新alertmanager通知配置失败！");
                }
                return code;
                //更新邮箱配置后需要，更新alertmanager的通知配置
            }catch(Exception e) {
                System.out.println("updateMailConfig error :"+e.getMessage());
                return 0;
            }

        }
    }

    @Override
    public Email queryMailConfig() {
        return mailMapper.queryMailConfig();
    }

    @Override
    public int createNoticeGroup(NoticeGroup noticeGroup) {
        return mailMapper.createNoticeGroup(noticeGroup);
    }
//    @Override
//    public int updateEmailAddress(NoticeGroup noticeGroup) {
//        int code = mailMapper.updateEmailAddress(noticeGroup);
//        try {
//            //删除邮箱配置后需要，更新alertmanager的通知配置
//            int config = ruleService.setAlertManagerSecret();
//            if (config == 0) {
//                System.out.println("更新alertmanager通知配置失败！");
//            }
//        }catch(Exception e) {
//            System.out.println("updateEmailAddress error :"+e.getMessage());
//            return 0;
//        }
//        return code;
//    }

    @Override
    public int deleteNoticeGroup(NoticeGroup noticeGroup) {
        return mailMapper.deleteNoticeGroup(noticeGroup);
    }

    @Override
    public PageInfo<NoticeGroup> queryNoticeGroupList(NoticeGroup noticeGroup) {
        // 设置分页参数; pageNum:页码, pageSize:每页大小
        PageHelper.startPage(noticeGroup.getPageNum(),noticeGroup.getPageSize());
        // 执行sql查询方法查询所有数据, 会自动分页
        List<NoticeGroup> list = mailMapper.queryNoticeGroupList(noticeGroup);
        return new PageInfo<NoticeGroup>(list);
    }

    @Override
    public List<NoticeGroup> queryNoticeGroupByIds(List<Long> noticeIds) {
        return mailMapper.queryNoticeGroupByIds(noticeIds);
    }

    @Override
    public List<EmailAddress> queryEmailAddressByNoticeId(Long noticeId) {
        return mailMapper.queryEmailAddressByNoticeId(noticeId);
    }

    @Override
    public int createEmailAddress(EmailAddressVo emailAddressVo) {
        return mailMapper.createEmailAddress(emailAddressVo);
    }

    @Override
    public int updateEmailAddress(EmailAddressVo emailAddressVo) {
        int code = mailMapper.updateEmailAddress(emailAddressVo);
        try {
            //删除邮箱配置后需要，更新alertmanager的通知配置
            int config = ruleService.setAlertManagerSecret();
            if (config == 0) {
                System.out.println("更新alertmanager通知配置失败！");
            }
        }catch(Exception e) {
            System.out.println("updateEmailAddress error :"+e.getMessage());
            return 0;
        }
        return code;
    }

    @Override
    public List<NoticeCount> queryNoticeCount(String tenantId) {
        return mailMapper.queryNoticeCount(tenantId);
    }

}
