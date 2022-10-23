package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/10/15:17
 */
@Data
public class RuleDesc implements Serializable {
    private Long id;
    private List<Desc> descList;
}
