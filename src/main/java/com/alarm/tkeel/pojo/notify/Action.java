package com.alarm.tkeel.pojo.notify;

import lombok.Data;

import javax.naming.ldap.PagedResultsControl;
import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/05/16:10
 */
@Data
public class Action implements Serializable {
    private String type;
    private String value;
    private Extras extras;
}
