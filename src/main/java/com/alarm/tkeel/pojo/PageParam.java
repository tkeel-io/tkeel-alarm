package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/19/9:43
 */
@Data
public class PageParam implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
}
