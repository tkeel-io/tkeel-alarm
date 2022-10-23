package com.alarm.tkeel.service;

import com.alarm.tkeel.pojo.AlarmHandle;
import com.alarm.tkeel.pojo.AlarmRecord;
import com.alarm.tkeel.pojo.AlarmRecordParamVo;
import com.alarm.tkeel.utils.PageInfo;

import java.util.List;

public interface AlarmService {
    PageInfo<AlarmRecord> queryAlarmRecord(AlarmRecordParamVo alarmRecordParamVo);

    Long countPendingRecord(AlarmRecord alarmRecord);

    int updateAlarmHandleOpinions(AlarmHandle alarmHandle);

    Long createAlarmRecord(AlarmRecord alarmRecord);

    Long createAlarmEvent(List<AlarmRecord> alarmRecord);

}
