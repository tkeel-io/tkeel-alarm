package com.alarm.tkeel.crd.alertmanager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Objects;
import java.util.StringJoiner;


/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/15/16:47
 */
@Data
public class WebhookConfig {
    private boolean send_resolved;
    private String url;
    private Integer max_alerts;
    private HttpConfig http_config;
}
