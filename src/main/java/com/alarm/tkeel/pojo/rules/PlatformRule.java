package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/31/11:30
 */
@Data
public class PlatformRule implements Serializable {
    private Long id;
    private String alarmDesc;
    private String promQl;
}
