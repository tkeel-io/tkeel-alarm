package com.alarm.tkeel.schedule;


import com.alarm.tkeel.config.WebHook;
import com.alarm.tkeel.pojo.Simulation;
import com.alarm.tkeel.utils.OkHttpCli;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class StaticScheduleTask {
    private static final String[] s=new String[]{"yuanyuan","wangmingze","wangtao","admin"};
    @Autowired
    private WebHook webHook;

    @Autowired
    private OkHttpCli okHttpCli;

    private Integer time = 0;
    @Scheduled(fixedRate=3000000)
    private void process() {
        String url = webHook.getUrl();
        Random random = new Random();
        Simulation simulation =new Simulation();
        simulation.setAlarmId(1001L);
        simulation.setAlarmLevel(1);
        simulation.setAlarmStatus(0);
        simulation.setTenantId("bDihQejJ");
        simulation.setObjectId("iotd-89565b55-853b-4d44-afe7-f9cce31fb6d6");
        simulation.setTelemetryId("202071003");
        simulation.setAlarmDesc("空调温度大于20度");
        simulation.setStartTime(System.currentTimeMillis()/1000);
        simulation.setAlarmValue(String.valueOf((random.nextInt(30)+21)));
        simulation.setUserId(s[(int) (Math.random()*4)]);
        simulation.setTitle("告警消息推送");
//        System.out.println(JSON.toJSONString(simulation));
//        String message = okHttpCli.doPostJson(url, JSON.toJSONString(simulation));
//        System.out.println(message);
    }
}
