package com.alarm.tkeel.pojo.alerts;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/24/10:17
 */
@Data
public class Result implements Serializable {
    private String status;
    private String receiver;
    private List<Alert> alerts;
}
