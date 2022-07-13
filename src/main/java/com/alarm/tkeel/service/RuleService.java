package com.alarm.tkeel.service;

import com.alarm.tkeel.pojo.PortalRuleDesc;
import com.alarm.tkeel.pojo.notify.DeviceStatus;
import com.alarm.tkeel.pojo.notify.RuleId;
import com.alarm.tkeel.pojo.notify.TelemetryStatus;
import com.alarm.tkeel.pojo.notify.TempStatus;
import com.alarm.tkeel.pojo.rules.*;
import com.alarm.tkeel.utils.PageInfo;
import io.kubernetes.client.openapi.ApiException;

import java.util.List;

public interface RuleService {
    PageInfo<Rule> queryRuleList(RuleParamVo ruleParamVo);

    int setNotice(UpdateNoticeParamVo updateNoticeParamVo);

    int setEnable(EnableParamVo enableParamVo);

    int deleteRule(DeleteParamVo deleteParamVo);

    List<PlatformRule> queryPlatformRuleList();

    Long createRule(RuleParamVo ruleParamVo);

//    int updateRule(RuleParamVo ruleParamVo);

    Rule queryRuleById(Long ruleId);

    List<Desc> queryRuleDesc(Long ruleId);

    List<Rule> queryAllRule();

    int insertPortalRuleDesc(PortalRuleDesc portalRuleDesc);

    int queryCountByTempId(String tempId);

    int updateTempStatus(TempStatus tempStatus);

    int updateDeviceStatus(DeviceStatus deviceStatus);

    int updateTelemetryStatus(TelemetryStatus telemetryStatus);

    int updateTelemetryStatusByRuleId(TelemetryStatus telemetryStatus);

    List<RuleId> queryRuleIdByTelemetryId(TelemetryStatus telemetryStatus);

    List<Rule> queryRuleStatus(String tenantId);
}
