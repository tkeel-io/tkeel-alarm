package com.alarm.tkeel.pojo.alerts;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/24/9:50
 */
@Data
public class Annotations implements Serializable {
    private String description;
    private String summary;
}
