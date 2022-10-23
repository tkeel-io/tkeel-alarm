package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/31/9:42
 */
@Data
public class EnableParamVo implements Serializable {
    // 是否启用，0:停用；1:启用
    private Integer enable;
    //规则ID
    private Long ruleId;
}
