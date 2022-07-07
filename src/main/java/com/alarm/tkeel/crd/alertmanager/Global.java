package com.alarm.tkeel.crd.alertmanager;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/22/11:58
 */
@Data
public class Global implements Serializable {
    private String resolve_timeout;
    private String smtp_smarthost;
    private String smtp_from;
    private String smtp_auth_username;
    private String smtp_auth_password;
    private boolean smtp_require_tls;
}
