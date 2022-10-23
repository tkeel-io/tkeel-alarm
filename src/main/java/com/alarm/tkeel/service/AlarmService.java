package com.alarm.tkeel.service;

import com.alarm.tkeel.pojo.*;
import com.alarm.tkeel.utils.PageInfo;

import java.util.List;

public interface AlarmService {
    PageInfo<AlarmRecord> queryAlarmRecord(AlarmRecordParamVo alarmRecordParamVo);

    int countPendingRecord(AlarmRecord alarmRecord);

    int updateAlarmHandleOpinions(AlarmHandle alarmHandle);

    int createAlarmRecord(List<AlarmRecord> alarmRecord);

}
