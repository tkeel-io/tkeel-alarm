package com.alarm.tkeel.pojo.metrics;

import com.alarm.tkeel.dao.AlarmMapper;
import com.alarm.tkeel.pojo.alerts.MetricCount;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/07/04/16:57
 */
@Service
public class Metrics implements MeterBinder {
    @Autowired
    private MeterRegistry registry;
    @Autowired
    private AlarmMapper alarmMapper;

    private AtomicLong countRecord = new AtomicLong(0);
    private List<MetricCount> metricCounts = null;
    private List<MetricCount> metricCountList = null;
    @Override
    public void bindTo(MeterRegistry meterRegistry) {
        //这里的MeterRegistry 是全局的
//        for(int i =0;i<4;i++) {
//            Gauge.builder("system.memory.used", map, x -> Double.parseDouble(x.get("value")))
//                    .tag("tenant_id", String.valueOf(i))
//                    .description("系统已用内存（byte）")
//                    .register(meterRegistry);
//        }
    }

    //定时器，定时改变数值
    @Scheduled(fixedRate = 3000000)
    public void recordAlarm(){
        metricCounts =  alarmMapper.queryAlarmCount();
        metricCountList = alarmMapper.queryAlarmLevel();
        if(metricCounts != null) {
            for (MetricCount metricCount : metricCounts) {
                countRecord.set(metricCount.getCount());
                Gauge.builder("alarm_record_total", countRecord::doubleValue)
                        .tag("tenant_id", metricCount.getTenantId())
                        .description("设备告警总数（条）")
                        .register(registry);
            }
        }
        if(metricCountList != null) {
            for (MetricCount metricCount : metricCountList) {
                AtomicLong countLevel = new AtomicLong(0);
                countLevel.set(metricCount.getCount());
                Tags tags = Tags.of("tenant_id", metricCount.getTenantId(),"alarm_level",metricCount.getAlarmLevel());
                Gauge.builder("alarm_level_total", countLevel::doubleValue)
                        .tags(tags)
                        .description("设备告警按级别分组统计（条）")
                        .register(registry);
            }
        }
    }
}
