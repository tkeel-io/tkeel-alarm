package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/06/10:06
 */
@Data
public class ImplementedPlugin implements Serializable {
    private List<Addons> addons;
    private Plugin plugin;
}
