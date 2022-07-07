package com.alarm.tkeel.crd.alertmanager;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/22/11:38
 */
@Data
public class AlertEntity implements Serializable {
    private Global global;
    private Route route;
    private List<Receiver> receivers;
}
