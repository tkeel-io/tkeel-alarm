package com.alarm.tkeel.crd.alertmanager;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/27/9:18
 */
@Data
public class HttpConfig implements Serializable {
    private Boolean follow_redirects;
}
