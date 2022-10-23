package com.alarm.tkeel.dao;

import com.alarm.tkeel.pojo.*;
import com.alarm.tkeel.pojo.alerts.MetricCount;

import java.util.List;


public interface AlarmMapper {

    List<AlarmRecord> queryAlarmRecord(AlarmRecordParamVo alarmRecordParamVo);

    int countPendingRecord(AlarmRecord alarmRecord);

    int updateAlarmHandleOpinions(AlarmHandle alarmHandle);

    int createAlarmRecord(List<AlarmRecord> alarmRecord);

    List<MetricCount> queryAlarmCount();

    List<MetricCount> queryAlarmLevel();
}
