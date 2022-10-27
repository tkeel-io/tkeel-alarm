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

//        "result====Result(status=firing, receiver=10152, alerts=[" +
//                "Alert(" +
//                "   labels=Labels(alertname=GGGGGG, " +
//                "   ruleId=10152, instance=10.233.83.14:6789, " +
//                "   job=core, model=null, status=紧急告警, " +
//                "   team=null, tenantId=H18fhe6d, " +
//                "   telemetry_id=fire_confidence, " +
//                "   objectId=null, alarmType=0, " +
//                "   alarmStrategy=0, alarmSource=1, alarmLevel=1, alarmValue=3.767499993244807), " +
//                "annotations=Annotations(description=置信度（火焰）>-10, summary=null), " +
//                "startsAt=2022-10-27T02:12:17.028Z, " +
//                "endsAt=0001-01-01T00:00:00Z, " +
//                "generatorURL=http://prometheus-k8s-0:9090/graph?g0.expr=avg_over_time%28entity_telemetry%7Btelemetry_id%3D%22fire_confidence%22%2Ctemplate_id%3D%22iotd-5dfc8ca9-01b1-463a-b60c-5df5f6c7cc81%22%2Ctenant_id%3D%22H18fhe6d%22%7D%5B1m%5D%29+%3E+-10&g0.tab=1, " +
//                "status=firing, receivers=null, fingerprint=feddc0016190b64a)" +
//                "])"

        System.out.println(JSON.toJSONString("firing===="+result.getStatus()));
        if (result.getStatus() != null && result.getStatus().equals("firing")) {
            List<Alert> alertList = result.getAlerts();
            Map<String,List<AlarmRecord>> alarmEventMap =new HashMap<>();

            System.out.println(JSON.toJSONString("alertList===="+alertList));
            System.out.println(JSON.toJSONString("alertList.size===="+alertList.size()));
            for (Alert alert : alertList) {
                System.out.println(JSON.toJSONString("alert===="+alert));
                if(alert.getLabels().getRuleId() == null){
                    System.out.println("规则ID为空，非设备告警，忽略！");
                    continue;
                }

                String recordHash = alert.getLabels().getRuleId() + "-" + alert.getLabels().getTenantId() + "-" + alert.getLabels().getObjectId();
                List<AlarmRecord> alarmEventList = alarmEventMap.get(recordHash);
                if (alarmEventList == null) {
                    alarmEventList = new ArrayList<>();
                    alarmEventMap.put(recordHash, alarmEventList);
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
                alarmRecord.setRecordHash(recordHash);
                alarmEventList.add(alarmRecord);
            }

            for (Map.Entry<String,List<AlarmRecord>> entry : alarmEventMap.entrySet()){
                String recordHash = entry.getKey();
                if (entry.getValue().size() <=0){
                    continue;
                }
                AlarmRecord alarmRecord = entry.getValue().get(0);
                if(alarmService.countPendingRecord(alarmRecord)==0){
                    Long id = alarmService.createAlarmRecord(alarmRecord);
                }
                alarmService.createAlarmEvent(entry.getValue());
            }
            System.out.println("alarmEventMap.size() ========= " +alarmEventMap.entrySet().size());


            alarmEventMap.size() ========= 0



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
