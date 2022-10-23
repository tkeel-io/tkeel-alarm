package com.alarm.tkeel.crd.alertmanager;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/22/14:08
 */
@Data
public class Receiver {
    private String name;
    private List<EmailConfig> email_configs = new ArrayList();
    private List<WebhookConfig> webhook_configs = new ArrayList();
}
