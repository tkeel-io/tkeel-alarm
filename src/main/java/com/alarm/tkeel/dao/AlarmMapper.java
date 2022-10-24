package com.alarm.tkeel.dao;

import com.alarm.tkeel.pojo.*;
import com.alarm.tkeel.pojo.alerts.MetricCount;

import java.util.List;


public interface AlarmMapper {

    List<AlarmRecord> queryAlarmRecord(AlarmRecordParamVo alarmRecordParamVo);

    Long countPendingRecord(AlarmRecord alarmRecord);

    int updateAlarmHandleOpinions(AlarmHandle alarmHandle);

    Long createAlarmRecord(AlarmRecord alarmRecord);

    Long createAlarmEvent(List<AlarmRecord> alarmRecord);

    int updateAlarmEventAlarmID(AlarmRecord alarmRecord);

    List<MetricCount> queryAlarmCount();

    List<MetricCount> queryAlarmLevel();
}
