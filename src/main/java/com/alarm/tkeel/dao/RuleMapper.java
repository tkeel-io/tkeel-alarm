package com.alarm.tkeel.dao;

import com.alarm.tkeel.pojo.PortalRuleDesc;
import com.alarm.tkeel.pojo.mail.BindingQuery;
import com.alarm.tkeel.pojo.notify.DeviceStatus;
import com.alarm.tkeel.pojo.notify.RuleId;
import com.alarm.tkeel.pojo.notify.TelemetryStatus;
import com.alarm.tkeel.pojo.notify.TempStatus;
import com.alarm.tkeel.pojo.rules.*;

import java.util.List;


public interface RuleMapper {

    List<Rule> queryRuleList(RuleParamVo ruleParamVo);

    int setNotice(UpdateNoticeParamVo updateNoticeParam);

    int setEnable(EnableParamVo enableParamVo);

    int deleteRule(DeleteParamVo deleteParamVo);

    List<PlatformRule> queryPlatformRuleList();

    Long createRule(Rule rule);

    int updateRule(Rule rule);

    Rule queryRuleById(Long ruleId);

    RuleParamVo queryRuleByCRD(Long ruleId);

    int insertRuleDesc(List<Desc> ruleDesc);

    int deleteRuleDesc(Long ruleId);

    List<Desc> queryRuleDesc(Long ruleId);

    List<Rule> queryAllRule();

    int insertPortalRuleDesc(PortalRuleDesc portalRuleDesc);

    int queryCountByTempId(String tempId);

    int queryNoticeByNoticeId(BindingQuery bindingQuery);

    int updateTempStatus(TempStatus tempStatus);

    int updateDeviceStatus(DeviceStatus deviceStatus);

    int updateTelemetryStatus(TelemetryStatus telemetryStatus);

    int updateTelemetryStatusByRuleId(TelemetryStatus telemetryStatus);

    List<RuleId> queryRuleIdByTelemetryId(TelemetryStatus telemetryStatus);

}
