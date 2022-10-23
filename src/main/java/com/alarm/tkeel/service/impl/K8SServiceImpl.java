package com.alarm.tkeel.service.impl;

import com.alarm.tkeel.crd.alertmanager.Global;
import com.alarm.tkeel.pojo.mail.Email;
import com.alarm.tkeel.pojo.rules.Rule;
import com.alarm.tkeel.service.K8SService;
import com.alarm.tkeel.service.MailService;
import com.alarm.tkeel.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/22/15:10
 */
@Service
public class K8SServiceImpl implements K8SService {
    @Autowired
    private RuleService ruleService;
    @Autowired
    private MailService mailService;

    @Override
    public int deleteSecret(String name, String namespace) {
        return 0;
    }

    @Override
    public int createSecret(String name, String namespace) {
        List<Rule> ruleList = ruleService.queryAllRule();
        Email email = mailService.queryMailConfig();
        if(email == null){
            System.out.println("email == null");
            return 0;
        }
        Global global = new Global();
        global.setResolve_timeout("5m");
        global.setSmtp_smarthost(email.getSmtpAddress());
        global.setSmtp_auth_username(email.getSmtpUserName());
        global.setSmtp_auth_password(email.getSmtpPassWord());
        global.setSmtp_from(email.getFromAddress());
        global.setSmtp_require_tls(email.getSsl() == 1 ? true : false);
        return 0;
    }

}
