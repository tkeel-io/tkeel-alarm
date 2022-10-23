package com.alarm.tkeel.dao;

import com.alarm.tkeel.pojo.*;

import java.util.List;


public interface PortalAlarmMapper {

    List<PortalAlarm> findAlarmRecord(PortalAlarmParamVo portalAlarmParamVo);

    List<PortalAlarm> findAllAlarmRecord();

    AlarmRecordCount resultAlarmRecordCount(PortalAlarmParamVo portalAlarmParamVo);

    AlarmEventCount resultAlarmEventCount(PortalAlarmParamVo portalAlarmParamVo);

    int updateAlarmStatus(UpdateStatusParam updateStatusParam);
}
