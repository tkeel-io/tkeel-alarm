package com.alarm.tkeel.pojo;

import lombok.Data;
import org.springframework.web.util.pattern.PathPattern;

import java.io.Serializable;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/05/27/16:24
 */
@Data
public class AlarmHandle implements Serializable {
    private Long alarmId;
    private Long ruleId;
    private Long tenantId;
    private Long handTime;
    private Integer alarmStatus;
    // 告警处理意见
    private String handOpinions;
}
