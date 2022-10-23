package com.alarm.tkeel.service;

import com.alarm.tkeel.pojo.*;
import com.alarm.tkeel.utils.PageInfo;

public interface PortalAlarmService {
    PageInfo<PortalAlarm> findAlarmEventRecord(int pageNum, int pageSize,PortalAlarmParamVo portalAlarmParamVo);

    PageInfo<PortalAlarm> findAllAlarmEventRecord(int pageNum, int pageSize);

    AlarmRecordCount resultAlarmRecordCount(PortalAlarmParamVo portalAlarmParamVo);

    AlarmEventCount resultAlarmEventCount(PortalAlarmParamVo portalAlarmParamVo);

    int updateAlarmEventStatus(UpdateStatusParam updateStatusParam);
}
