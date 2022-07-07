package com.alarm.tkeel.pojo;

import lombok.Data;

import javax.xml.soap.SAAJResult;
import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/06/10:02
 */
@Data
public class Plugin implements Serializable {
    private String id;
    private String version;
}
