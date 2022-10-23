package com.alarm.tkeel.controller;


import com.alarm.tkeel.pojo.AlarmRecord;
import com.alarm.tkeel.pojo.alerts.Alert;
import com.alarm.tkeel.pojo.alerts.Result;
import com.alarm.tkeel.service.AlarmService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/webhook")
@Api(value = "webhook接口",tags = "webhook接口", description = "用于接收Prometheus推送告警记录")
public class WebHookController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AlarmService alarmService;

    @PostMapping("/demo")
    @ApiOperation(value = "接收告警记录推送",notes = "接收告警记录推送",produces = "application/json")
    public void demo(@RequestBody Result result){
        System.out.println(JSON.toJSONString("result===="+result));
        if (result.getStatus() != null && result.getStatus().equals("firing")) {
            List<Alert> alertList = result.getAlerts();
            List<AlarmRecord> alarmRecordList =new ArrayList<>();
            for (Alert alert : alertList) {
                if(alert.getLabels().getRuleId() == null){
                    System.out.println("规则ID为空，非设备告警，忽略！");
                    continue;
                }
                AlarmRecord alarmRecord =new AlarmRecord();
                alarmRecord.setAlarmName(alert.getLabels().getAlertname());
                alarmRecord.setAlarmLevel(alert.getLabels().getAlarmLevel());
                alarmRecord.setAlarmSource(alert.getLabels().getAlarmSource());
                alarmRecord.setAlarmStatus(0);
                alarmRecord.setAlarmType(alert.getLabels().getAlarmType());
                alarmRecord.setAlarmStrategy(alert.getLabels().getAlarmStrategy());
                alarmRecord.setObjectId(alert.getLabels().getObjectId());
                alarmRecord.setTenantId(alert.getLabels().getTenantId());
                alarmRecord.setTelemetryId(alert.getLabels().getTelemetry_id());
                alarmRecord.setAlarmDesc(alert.getAnnotations().getDescription());
                alarmRecord.setStartTime(formatTime(alert.getStartsAt())/1000);
                alarmRecord.setDeleted(0);
                alarmRecord.setAlarmValue(alert.getLabels().getAlarmValue());
                alarmRecord.setRuleId(alert.getLabels().getRuleId());
                alarmRecord.setRecordHash(alarmRecord.getRuleId() + "-" + alarmRecord.getTenantId() + "-" + alarmRecord.getObjectId());
                if(alarmService.countPendingRecord(alarmRecord)==0){
                    alarmRecordList.add(alarmRecord);
                }
            }
           // 此处批量新增告警记录
            if(alarmRecordList != null || alarmRecordList.size() > 0){
                System.out.println("alarmRecordList ====== "+JSON.toJSONString(alarmRecordList));
                alarmService.createAlarmRecord(alarmRecordList);
            }
        }else {
            System.out.println("result.getStatus() ========= " +result.getStatus());
        }
    }


    //把utc格式日期转换为常见的格式
    public static Long formatTime(String timeStr){
        SimpleDateFormat dateFormat = null;
        //进行转化时区
        if(timeStr.contains("Z")) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
        }else {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+0000");
        }
        Date myDate = null;
        try {
            myDate = dateFormat.parse (timeStr.replace("Z","+0000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //转换为年月日时分秒
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myDate.getTime();
    }

}
