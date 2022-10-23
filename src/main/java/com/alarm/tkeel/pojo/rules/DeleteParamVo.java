package com.alarm.tkeel.pojo.rules;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/31/9:42
 */
@Data
public class DeleteParamVo implements Serializable {
    // 是否删除 ，1:删除
    private Integer deleted;
    //规则ID
    private Long ruleId;
}
