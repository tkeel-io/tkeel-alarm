package com.alarm.tkeel.service.impl;

import com.alarm.tkeel.crd.alertmanager.*;
import com.alarm.tkeel.dao.MailMapper;
import com.alarm.tkeel.dao.RuleMapper;
import com.alarm.tkeel.pojo.PortalRuleDesc;
import com.alarm.tkeel.pojo.mail.Email;
import com.alarm.tkeel.pojo.mail.EmailAddressVo;
import com.alarm.tkeel.pojo.mail.NoticeGroup;
import com.alarm.tkeel.pojo.notify.DeviceStatus;
import com.alarm.tkeel.pojo.notify.RuleId;
import com.alarm.tkeel.pojo.notify.TelemetryStatus;
import com.alarm.tkeel.pojo.notify.TempStatus;
import com.alarm.tkeel.pojo.rules.*;
import com.alarm.tkeel.service.CRDService;
import com.alarm.tkeel.service.RuleService;
import com.alarm.tkeel.utils.Constant;
import com.alarm.tkeel.utils.PageInfo;
import com.alibaba.fastjson.JSON;
import com.coreos.monitoring.models.V1PrometheusRule;
import com.coreos.monitoring.models.V1PrometheusRuleSpec;
import com.coreos.monitoring.models.V1PrometheusRuleSpecGroups;
import com.coreos.monitoring.models.V1PrometheusRuleSpecRules;
import com.github.pagehelper.PageHelper;
//import io.fabric8.openshift.api.model.monitoring.v1alpha1.Receiver;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Secret;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.util.Config;
import org.ho.yaml.Yaml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RuleServiceImpl implements RuleService {

    @Resource
    private RuleMapper ruleMapper;
    @Resource
    private MailMapper mailMapper;
    @Autowired
    private CRDService crdService;

    @Override
    public PageInfo<Rule> queryRuleList(RuleParamVo ruleParamVo) {
        // 设置分页参数; pageNum:页码, pageSize:每页大小
        PageHelper.startPage(ruleParamVo.getPageNum(), ruleParamVo.getPageSize());
        // 执行sql查询方法查询所有数据, 会自动分页
        List<Rule> list = ruleMapper.queryRuleList(ruleParamVo);
        return new PageInfo<Rule>(list);
    }

    @Override
    public int setNotice(UpdateNoticeParamVo updateNoticeParam) {
        int code = 0;
        int result = ruleMapper.setNotice(updateNoticeParam);
        if (result > 0) {
            code = setAlertManagerSecret();
        } else {
            return 0;
        }
        return code;
    }

    @Override
    public int setEnable(EnableParamVo enableParamVo) {
        int result = ruleMapper.setEnable(enableParamVo);
        // 当停用规则时，需删除通知，否则会持续告警
//        if(enableParamVo.getEnable() == 0){
//            UpdateNoticeParamVo updateNoticeParamVo = new UpdateNoticeParamVo();
//            updateNoticeParamVo.setRuleId(enableParamVo.getRuleId());
//            updateNoticeParamVo.setNoticeId(null);
//            int res = ruleMapper.setNotice(updateNoticeParamVo);
//            if(res > 0 ) {
//               setAlertManagerSecret();
//            }else {
//                System.out.println("setEnable-setNotice error");
//                return 0;
//            }
//        }
        // 重新设置通知配置
        setAlertManagerSecret();
        if (result > 0) {
            //此处需要调用CRD接口配置规则
            RuleParamVo rule = ruleMapper.queryRuleByCRD(enableParamVo.getRuleId());
            if (rule != null) {
                result = setRuleConfigCR(rule, enableParamVo.getEnable());
                if (result != 200) {
                    System.out.println("设置规则配置失败，id = " + rule.getRuleId());
                }
            } else {
                result = 500;
                System.out.println("获取规则失败，id = " + rule.getRuleId());
            }
        }
        return result;
    }

    @Override
    public int deleteRule(DeleteParamVo deleteParamVo) {
//        int ruleCode = 0 ;
//        int alertCode = 0;
//        int result = ruleMapper.deleteRule(deleteParamVo);
//        if(result > 0 ){
//            // 删除规则时，如已启用则需要先停用再删除。
//            try {
//                ruleCode = crdService.deletePrometheusRuleCR(Constant.TKEEL_ALARM_NAMESPACE,Constant.RULE_NAME_PREFIX+deleteParamVo.getRuleId());
////                alertCode = crdService.deleteAlertManagerCR(Constant.TKEEL_ALARM_NAMESPACE,Constant.ALERT_NAME_PREFIX+deleteParamVo.getRuleId());
//            } catch (ApiException e) {
//                System.out.println("rule_id = "+deleteParamVo.getRuleId());
//                System.out.println("deleteRule error "+e.getMessage());
//                e.printStackTrace();
//            }
//
//        }
        return ruleMapper.deleteRule(deleteParamVo);
    }

    @Override
    public List<PlatformRule> queryPlatformRuleList() {
        return ruleMapper.queryPlatformRuleList();
    }

    @Override
    public Long createRule(RuleParamVo ruleParamVo) {
        Long ruleId = null;
        Rule rule = new Rule();
//        RuleDesc ruleDesc =new RuleDesc();
        List<Desc> descList = new ArrayList<>();
        rule.setTenantId(ruleParamVo.getTenantId());
        rule.setRuleName(ruleParamVo.getRuleName());
        rule.setAlarmType(ruleParamVo.getAlarmType());
        rule.setAlarmRuleType(ruleParamVo.getAlarmRuleType());
        rule.setAlarmLevel(ruleParamVo.getAlarmLevel());
        rule.setAlarmSourceObject(ruleParamVo.getAlarmSourceObject());
        rule.setCondition(ruleParamVo.getCondition());
        // 遍历规则条件，拼接promQL表达式
        StringBuffer promQl = new StringBuffer();
        StringBuffer desc = new StringBuffer();
        StringBuffer platRuleIds = new StringBuffer();
        if (rule.getAlarmSourceObject() == 1) {
            // 当告警源对象为 设备时
            rule.setTempId(ruleParamVo.getTempId());
            rule.setTempName(ruleParamVo.getTempName());
            rule.setDeviceId(ruleParamVo.getDeviceId());
            rule.setDeviceName(ruleParamVo.getDeviceName());
            rule.setTelemetryId(ruleParamVo.getTelemetryId());
            // 布尔 或者 枚举 时
            for (DeviceCondition deviceCondition : ruleParamVo.getDeviceCondition()) {
                Desc de = new Desc();
                // 遥测为 枚举 或者 比尔类型
                if (deviceCondition.getTelemetryType() == 0 || deviceCondition.getTelemetryType() == 1) {
//                    promQl.append(ruleParamVo.getTenantId());
                    promQl.append(Constant.METRIC_NAMES);
//                    promQl.append(deviceCondition.getTelemetryId());
                    promQl.append("{");
                    promQl.append("tenant_id=");
                    promQl.append("\"");
                    promQl.append(ruleParamVo.getTenantId());
                    promQl.append("\"");
                    promQl.append(",");
                    promQl.append("telemetry_id=");
                    promQl.append("\"");
                    promQl.append(deviceCondition.getTelemetryId());
                    promQl.append("\"");
                    promQl.append(",");
                    if (rule.getTempId() != null && !rule.getTempId().equals("")) {
                        promQl.append(",");
                        promQl.append("template_id=");
                        promQl.append("\"");
                        promQl.append(rule.getTempId());
                        promQl.append("\"");
                    }
                    if (rule.getDeviceId() != null && !rule.getDeviceId().equals("")) {
                        promQl.append(",");
                        promQl.append("entity_id=");
                        promQl.append("\"");
                        promQl.append(rule.getDeviceId());
                        promQl.append("\"");
                    }
                    promQl.append("}");
                    promQl.append(operator(deviceCondition.getOperator()));
                    promQl.append(deviceCondition.getValue());
                    desc.append(deviceCondition.getTelemetryName() + operator(deviceCondition.getOperator()) + deviceCondition.getValue() + ",");
                    if (ruleParamVo.getCondition().equals("and") && ruleParamVo.getDeviceCondition().size() >= 2) {
                        promQl.append(" and ");
                    } else if (ruleParamVo.getCondition().equals("and") && ruleParamVo.getDeviceCondition().size() >= 2) {
                        promQl.append(" or ");
                    }
                } else {
                    // 平均值、最大、最小值
                    if (deviceCondition.getTime() > 0) {
                        if (deviceCondition.getPolymerize().equals("avg")) {
                            promQl.append("avg_over_time(");
                        }
                        if (deviceCondition.getPolymerize().equals("max")) {
                            promQl.append("max_over_time(");
                        }
                        if (deviceCondition.getPolymerize().equals("min")) {
                            promQl.append("min_over_time(");
                        }
                    }
                    promQl.append(Constant.METRIC_NAMES);
                    promQl.append("{");
                    promQl.append("tenant_id=");
                    promQl.append("\"");
                    promQl.append(ruleParamVo.getTenantId());
                    promQl.append("\"");
                    ;
                    promQl.append(",");
                    promQl.append("telemetry_id=");
                    promQl.append("\"");
                    promQl.append(deviceCondition.getTelemetryId());
                    promQl.append("\"");
                    if (rule.getTempId() != null && !rule.getTempId().equals("")) {
                        promQl.append(",");
                        promQl.append("template_id=");
                        promQl.append("\"");
                        promQl.append(rule.getTempId());
                        promQl.append("\"");
                    }
                    if (rule.getDeviceId() != null && !rule.getDeviceId().equals("")) {
                        promQl.append(",");
                        promQl.append("entity_id=");
                        promQl.append("\"");
                        promQl.append(rule.getDeviceId());
                        promQl.append("\"");
                    }
                    promQl.append("}");
                    if (deviceCondition.getTime() > 0) {
                        promQl.append("[" + deviceCondition.getTime() + "m]");
                    }
                    if (deviceCondition.getTime() != 0) {
                        promQl.append(")");
                    }
                    promQl.append(operator(deviceCondition.getOperator()));
                    promQl.append(deviceCondition.getValue());
                    desc.append(deviceCondition.getTelemetryName() + operator(deviceCondition.getOperator()) + deviceCondition.getValue() + ",");
                    if (ruleParamVo.getCondition().equals("and") && ruleParamVo.getDeviceCondition().size() >= 2) {
                        promQl.append(" and ");
                    } else if (ruleParamVo.getCondition().equals("or") && ruleParamVo.getDeviceCondition().size() >= 2) {
                        promQl.append(" or ");
                    }
                }
                de.setTenantId(ruleParamVo.getTenantId());
                de.setTempId(ruleParamVo.getTempId());
                de.setDeviceId(ruleParamVo.getDeviceId());
                de.setTelemetryId(deviceCondition.getTelemetryId());
                de.setTelemetryName(deviceCondition.getTelemetryName());
                de.setTelemetryType(deviceCondition.getTelemetryType());
                de.setAlarmSourceObject(ruleParamVo.getAlarmSourceObject());
                de.setTime(deviceCondition.getTime());
                de.setOperator(deviceCondition.getOperator());
                de.setCondition(deviceCondition.getCondition());
                de.setPolymerize(deviceCondition.getPolymerize());
                de.setLabel(deviceCondition.getLabel());
                de.setValue(deviceCondition.getValue());
                descList.add(de);
            }
            if (ruleParamVo.getCondition().equals("and")) {
                rule.setPromQl(intercept(promQl.toString(), "and"));
            } else if (ruleParamVo.getCondition().equals("or")) {
                rule.setPromQl(intercept(promQl.toString(), "or"));
            }
        } else {
            Desc de = new Desc();
            //当告警源对象为 平台时
            if (ruleParamVo.getPlatformRuleList() != null) {
                for (PlatformRule platformRule : ruleParamVo.getPlatformRuleList()) {
                    desc.append(platformRule.getAlarmDesc() + ",");
                    promQl.append(replaceStr(platformRule.getPromQl(), ruleParamVo.getTenantId()) + " or ");
                    platRuleIds.append(platformRule.getId() + ",");
                }
            } else {
                return -1L;
            }
            de.setTenantId(ruleParamVo.getTenantId());
            de.setAlarmSourceObject(ruleParamVo.getAlarmSourceObject());
            de.setPlatRuleId(platRuleIds.toString().substring(0, platRuleIds.toString().lastIndexOf(",")));
            descList.add(de);
            rule.setPromQl(intercept(promQl.toString(), "or"));
        }
        rule.setRuleDesc(desc.toString().substring(0, desc.toString().lastIndexOf(",")));

        if (ruleParamVo.getRuleId() != null) {
            // 更新规则
            rule.setRuleId(ruleParamVo.getRuleId());
            rule.setTempStatus(0);
            rule.setDeviceStatus(0);
            rule.setTelemetryStatus(0);
            ruleMapper.updateRule(rule);
            // 先删除之前的记录
            ruleMapper.deleteRuleDesc(ruleParamVo.getRuleId());
            ruleId = ruleParamVo.getRuleId();
            if (ruleParamVo.getEnable() == 1) {
                // 当规则状态为启用状态时-更新CRD规则配置
                int result = setRuleConfigCR(ruleParamVo, 2);
                if (result != 200) {
                    System.out.println("更新规则配置失败，id = " + rule.getRuleId());
                }
            }
        } else {
            ruleId = ruleMapper.createRule(rule);
        }
        if (ruleId > 0) {
            List<Desc> descs = descList;
            descs = descs.stream().map((item) -> {
                item.setRuleId(rule.getRuleId());
                return item;
            }).collect(Collectors.toList());
//            ruleDesc.setDescList(descs);
            //插入规则描述
            ruleMapper.insertRuleDesc(descs);
        }
        return ruleId;
    }

    @Override
    public Rule queryRuleById(Long ruleId) {
        return ruleMapper.queryRuleById(ruleId);
    }

    @Override
    public List<Desc> queryRuleDesc(Long ruleId) {
        return ruleMapper.queryRuleDesc(ruleId);
    }

    @Override
    public List<Rule> queryAllRule() {
        return ruleMapper.queryAllRule();
    }

    @Override
    public int insertPortalRuleDesc(PortalRuleDesc portalRuleDesc) {
        return ruleMapper.insertPortalRuleDesc(portalRuleDesc);
    }

    @Override
    public int queryCountByTempId(String tempId) {
        return ruleMapper.queryCountByTempId(tempId);
    }

    @Override
    public int updateTempStatus(TempStatus tempStatus) {
        return ruleMapper.updateTempStatus(tempStatus);
    }

    @Override
    public int updateDeviceStatus(DeviceStatus deviceStatus) {
        return ruleMapper.updateDeviceStatus(deviceStatus);
    }

    @Override
    public int updateTelemetryStatus(TelemetryStatus telemetryStatus) {
        //通过遥测ID与租户ID查出规则ID
        List<RuleId> longList = ruleMapper.queryRuleIdByTelemetryId(telemetryStatus);
        if (longList == null || longList.size() <= 0) {
            System.out.println("updateTelemetryStatus : 未找到相关遥测信息，" + JSON.toJSONString(telemetryStatus.getTelemetryId()));
            return 1;
        }
        List<RuleId> ruleId = longList.stream().distinct().collect(Collectors.toList());
        List<Long> longs = new ArrayList<>();
        for (RuleId id : ruleId) {
            longs.add(id.getRuleId());
        }
        // 先更新规则表tkeel_alarm_rule中的遥测状态
        TelemetryStatus status = new TelemetryStatus();
        status.setRuleId(longs);
        status.setTelemetryStatus(telemetryStatus.getTelemetryStatus());
        status.setTenantId(telemetryStatus.getTenantId());

        int ruleCode = ruleMapper.updateTelemetryStatusByRuleId(status);
        // 再更新详情tkeel_alarm_rule_desc表中遥测状态值
        int descCode = ruleMapper.updateTelemetryStatus(telemetryStatus);
        return (ruleCode + descCode) > 1 ? 1 : 0;
    }

    @Override
    public int updateTelemetryStatusByRuleId(TelemetryStatus telemetryStatus) {
        return ruleMapper.updateTelemetryStatusByRuleId(telemetryStatus);
    }

    @Override
    public List<RuleId> queryRuleIdByTelemetryId(TelemetryStatus telemetryStatus) {
        return ruleMapper.queryRuleIdByTelemetryId(telemetryStatus);
    }

    @Override
    public List<Rule> queryRuleStatus(String tenantId) {
        return ruleMapper.queryRuleStatus(tenantId);
    }

    /**
     * 截取最后一个key之前的字符串
     *
     * @param str
     * @param key
     * @return
     */
    private String intercept(String str, String key) {
        if (str.contains(key)) {
            String s = str.substring(0, str.lastIndexOf(key));
            if (s.contains("'")) {
                return s.replace("'", "\"");
            } else {
                return s;
            }

        } else {
            if (str.contains("'")) {
                return str.replace("'", "\"");
            } else {
                return str;
            }
        }
    }

    /**
     * 返回运算符
     *
     * @param value
     * @return
     */
    private String operator(String value) {
        if (value.equals("eq")) {
            return "==";
        } else if (value.equals("ne")) {
            return "!=";
        } else if (value.equals("gt")) {
            return ">";
        } else if (value.equals("lt")) {
            return "<";
        } else if (value.equals("ge")) {
            return ">=";
        } else if (value.equals("le")) {
            return "<=";
        } else {
            return "";
        }
    }

    /**
     * 替换字符串
     *
     * @param str
     * @param tenantId
     * @return
     */
    public String replaceStr(String str, String tenantId) {
        if (str != null && !str.equals("") && str.contains("tkeel-tenant")) {
            return str.replaceAll("tkeel-tenant", tenantId);
        } else {
            return str;
        }
    }

    /**
     * 设置告警规则配置到CR
     *
     * @return
     */
    private int setRuleConfigCR(RuleParamVo rule, int enable) {
        if (enable == 1) {
            //创建规则
            V1PrometheusRule v1PrometheusRule = getRuleConfig(rule);
            try {
                return crdService.createPrometheusRuleCR(v1PrometheusRule);
            } catch (ApiException e) {
                System.out.println("createPrometheusRuleCR error " + e.getMessage());
                return 0;
            }

        } else if (enable == 0) {
            // 删除规则
            try {
                return crdService.deletePrometheusRuleCR(Constant.TKEEL_ALARM_NAMESPACE, Constant.RULE_NAME_PREFIX + rule.getRuleId());
            } catch (ApiException e) {
                System.out.println("deletePrometheusRuleCR error " + e.getMessage());
                return 0;
            }
        } else {
            // 更新规则
            try {
                RuleParamVo ruleParamVo = ruleMapper.queryRuleByCRD(rule.getRuleId());
                V1PrometheusRule v1PrometheusRule = getRuleConfig(ruleParamVo);
                return crdService.updatePrometheusRuleCR(v1PrometheusRule, Constant.TKEEL_ALARM_NAMESPACE, Constant.RULE_NAME_PREFIX + rule.getRuleId());
            } catch (ApiException e) {
                System.out.println("updatePrometheusRuleCR error " + e.getMessage());
                return 0;
            }
        }
    }

    /**
     * 设置告警规则通知CR
     * @return
     */
//    private int setAlertManagerCR(UpdateNoticeParamVo updateNoticeParamVo){
//        StringBuffer email_address = new StringBuffer();
//        List<Long> longList = new ArrayList<>();
//        if(!updateNoticeParamVo.getNoticeId().contains(",")){
//            longList.add(Long.valueOf(updateNoticeParamVo.getNoticeId()));
//        }else {
//            List<String> collect = Stream.of(updateNoticeParamVo.getNoticeId().split(",")).collect(Collectors.toList());
//            longList = collect.stream().map(Long::valueOf).collect(Collectors.toList());
//        }
//        List<NoticeGroup> noticeGroups = mailMapper.queryNoticeGroupByIds(longList);
//        if(noticeGroups != null){
//            for (NoticeGroup noticeGroup : noticeGroups){
//                email_address.append(noticeGroup.getEmailAddress());
//                email_address.append(",");
//            }
//            email_address.deleteCharAt(email_address.length() - 1);
//        }
//        AlertmanagerConfig alertmanagerConfig =new AlertmanagerConfig();
//        alertmanagerConfig.setKind(Constant.ALERT_KIND);
//        alertmanagerConfig.setApiVersion(Constant.ALERT_API_VERSION);
//
//        V1ObjectMeta objectMeta =new V1ObjectMeta();
//        objectMeta.setNamespace(Constant.TKEEL_ALARM_NAMESPACE);
//        objectMeta.setName(Constant.ALERT_NAME_PREFIX+updateNoticeParamVo.getRuleId());
//        Map<String,String> label =new HashMap<>();
//        label.put("alertmanagerConfig","example");
//        objectMeta.setLabels(label);
//
//        AlertmanagerConfigSpec alertmanagerConfigSpec =new AlertmanagerConfigSpec();
//        List<String> list = new ArrayList<>();
//        list.add("rule_id");
//        Route route =new Route();
//        route.setReceiver("default-receiver");
//        route.setGroupInterval("60s");
//        route.setRepeatInterval("60s");
//        route.setGroupWait("60s");
//        route.setGroupBy(list);
//        List<Routes> routesList = new ArrayList<>();
//        Routes routes =new Routes();
//        routes.setReceiver(updateNoticeParamVo.getRuleId().toString());
//        Map<String,String> match_map = new HashMap<>();
//        match_map.put("tenant_id",updateNoticeParamVo.getTenantId());
//        match_map.put("rule_id",updateNoticeParamVo.getRuleId().toString());
//        routes.setMatch(match_map);
//        routesList.add(routes);
//        route.setRoutes(routesList);
//
//        List<Receiver> receiverList =new ArrayList<>();
//        List<EmailConfig> emailConfigList =new ArrayList<>();
//        Receiver receiver = new Receiver();
//        receiver.setName(updateNoticeParamVo.getRuleId().toString());
//        EmailConfig emailConfig = new EmailConfig();
//        emailConfig.setTo(email_address.toString());
//        emailConfig.setSendResolved(true);
//        emailConfigList.add(emailConfig);
//        receiver.setEmailConfigs(emailConfigList);
//        receiverList.add(receiver);
//
//        alertmanagerConfigSpec.setRoute(route);
//        alertmanagerConfigSpec.setReceivers(receiverList);
//
//        alertmanagerConfig.setMetadata(objectMeta);
//        alertmanagerConfig.setSpec(alertmanagerConfigSpec);
//
//        int code = 0;
//        try {
//            code = crdService.createAlertManagerCR(alertmanagerConfig);
//        } catch (ApiException e) {
//            System.out.println("createAlertManagerCR error " +e.getMessage());
//        }
//        System.out.println("createAlertManagerCR code = "+code);
//        return code;
//    }

    /**
     * 设置AlertManager告警通知Secret
     *
     * @return
     */
    public int setAlertManagerSecret() {
        int ret =0;
        for(String name:Constant.ALERT_MANAGER_NAME){
            ret +=setAlertManagerSecret(name);
        }
        return ret>0?1:0;
    }

    public int setAlertManagerSecret(String secretName) {
        List<Rule> ruleList = ruleMapper.queryAllRule();
        Email email = mailMapper.queryMailConfig();
        if (ruleList == null) {
            System.out.println("setAlertManagerSecret Rule == null");
            return 0;
        }
        if (email == null) {
            System.out.println("setAlertManagerSecret email == null");
            return 0;
        }
        Global global = new Global();
        global.setResolve_timeout("5m");
        global.setSmtp_smarthost(email.getSmtpAddress() + ":" + email.getPort());
        global.setSmtp_auth_username(email.getSmtpUserName());
        global.setSmtp_auth_password(email.getSmtpPassWord());
        global.setSmtp_from(email.getFromAddress());
        global.setSmtp_require_tls(email.getSsl() == 1 ? true : false);

        V1Secret v1Secret = new V1Secret();
        v1Secret.setApiVersion("v1");
        v1Secret.setKind(Constant.SECRET_KIND);
        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        v1ObjectMeta.setNamespace(Constant.TKEEL_ALARM_NAMESPACE);
        v1ObjectMeta.setName(secretName);
        v1ObjectMeta.setAnnotations(null);
        v1Secret.setMetadata(v1ObjectMeta);
        v1Secret.setType("Opaque");

        Route route = new Route();
        route.setReceiver("default");
        route.setGroup_wait("5s");
        route.setGroup_interval("5s");
        route.setRepeat_interval("5s");
        route.setGroup_by(new String[]{"ruleId"});

        Receiver receiver1 = new Receiver();
        receiver1.setName("default");
        List<WebhookConfig> webhookConfigsList = new ArrayList<>();
        WebhookConfig webhookConfig = new WebhookConfig();
        webhookConfig.setSend_resolved(true);
        webhookConfig.setUrl("http://tkeel-alarm.keel-system:31239/webhook/demo");
        webhookConfigsList.add(webhookConfig);

        Receiver receiver2 = new Receiver();
        receiver2.setName("prometheus");
        List<WebhookConfig> webhookConfigsList2 = new ArrayList<>();
        WebhookConfig webhookConfig2 = new WebhookConfig();
        webhookConfig2.setSend_resolved(true);
        webhookConfig2.setUrl("http://notification-manager-svc.kubesphere-monitoring-system.svc:19093/api/v2/alerts");
        webhookConfigsList2.add(webhookConfig2);
        receiver2.setWebhook_configs(webhookConfigsList2);

        Receiver receiver3 = new Receiver();
        receiver3.setName("event");
        List<WebhookConfig> webhookConfigsList3 = new ArrayList<>();
        WebhookConfig webhookConfig3 = new WebhookConfig();
        webhookConfig3.setSend_resolved(false);
        webhookConfig3.setUrl("http://notification-manager-svc.kubesphere-monitoring-system.svc:19093/api/v2/alerts");
        webhookConfigsList3.add(webhookConfig3);
        receiver3.setWebhook_configs(webhookConfigsList3);


        Receiver receiver4 = new Receiver();
        receiver4.setName("auditing");
        List<WebhookConfig> webhookConfigsList4 = new ArrayList<>();
        WebhookConfig webhookConfig4 = new WebhookConfig();
        webhookConfig4.setSend_resolved(false);
        webhookConfig4.setUrl("http://notification-manager-svc.kubesphere-monitoring-system.svc:19093/api/v2/alerts");
        webhookConfigsList4.add(webhookConfig4);
        receiver4.setWebhook_configs(webhookConfigsList4);

        List<Receiver> receiverList = new ArrayList<>();
        List<Routes> routesList = new ArrayList<>();

        receiverList.add(receiver1);
        receiverList.add(receiver2);
        receiverList.add(receiver3);
        receiverList.add(receiver4);

        Receiver receiver_webhook = new Receiver();
        receiver_webhook.setName("webhook");
        receiver_webhook.setWebhook_configs(webhookConfigsList);

        for (Rule rule : ruleList) {
            Routes routes = new Routes();
            List<Long> noticeIds = getNoticeId(rule.getNoticeId());
            //获取所有启用规则的通知列表
            List<EmailAddressVo> noticeGroups = mailMapper.queryEmailAddress(noticeIds);
            if (noticeGroups == null) {
                System.out.println("setAlertManagerSecret emailList == null");
                return 0;
            }
            routes.setReceiver(String.valueOf(rule.getRuleId()));
//            routes.setGroup_by(new String[]{"ruleId"});
            Map<String, String> match_map = new HashMap<>();
//            match_map.put("alerttype",".*");
            match_map.put("tenantId", rule.getTenantId());
            match_map.put("ruleId", String.valueOf(rule.getRuleId()));
            routes.setMatch(match_map);

            Routes prometheusRoutes = new Routes();
            Routes eventRoutes = new Routes();
            Routes auditingRoutes = new Routes();
            Map<String, String> prometheus_map = new HashMap<>();
            Map<String, String> event_map = new HashMap<>();
            Map<String, String> auditing_map = new HashMap<>();
            prometheus_map.put("alerttype", ".*");
            event_map.put("alerttype", "event");
            auditing_map.put("alerttype", "auditing");

            prometheusRoutes.setReceiver("prometheus");
            prometheusRoutes.setMatch_re(prometheus_map);
//        prometheusRoutes.setContinueText(false);

            eventRoutes.setReceiver("event");
            eventRoutes.setMatch(event_map);
//        eventRoutes.setContinueText(false);
            eventRoutes.setGroup_interval("30s");

            auditingRoutes.setReceiver("auditing");
            auditingRoutes.setMatch(auditing_map);
//        auditingRoutes.setContinueText(false);
            auditingRoutes.setGroup_interval("30s");

            routesList.add(routes);
            routesList.add(prometheusRoutes);
            routesList.add(eventRoutes);
            routesList.add(auditingRoutes);
            route.setRoutes(routesList);

            Receiver receiver = new Receiver();
            receiver.setName(String.valueOf(rule.getRuleId()));

            List<EmailConfig> emailConfigList = new ArrayList<>();
            EmailConfig emailConfig = new EmailConfig();
            StringBuffer stringBuffer = new StringBuffer();
            for (EmailAddressVo notice : noticeGroups) {
                if (rule.getNoticeId().contains(notice.getNoticeId().toString())) {
                    stringBuffer.append(notice.getEmailAddress());
                    stringBuffer.append(",");
                }
            }
            emailConfig.setTo(stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString());
            emailConfig.setSend_resolved(true);
            emailConfigList.add(emailConfig);
            receiver.setEmail_configs(emailConfigList);
            receiver.setWebhook_configs(webhookConfigsList);

            receiverList.add(receiver);
        }
        Map<String, byte[]> map = new HashMap<>();

        // 设置平台通知默认参数
//        if(route.getRoutes() == null) {
//            route.setRoutes(setRoutesConfig());
//        }
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setGlobal(global);
        alertEntity.setRoute(route);
        alertEntity.setReceivers(receiverList);

        File dumpFile = new File(System.getProperty("user.dir") + "/alertmanager.yaml");
        try {
            Yaml.dump(alertEntity, dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] be = encryptToBase64(System.getProperty("user.dir") + "/alertmanager.yaml");
        map.put("alertmanager.yaml", be);

        v1Secret.setData(map);
        ApiResponse<V1Status> apiResponse = null;

        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);
            CoreV1Api api = new CoreV1Api();
            apiResponse = api.deleteNamespacedSecretWithHttpInfo(secretName, Constant.TKEEL_ALARM_NAMESPACE, null, null, null, null, null, null);
            System.out.println("v1Status == " + apiResponse.getStatusCode());
            V1Secret secret = api.createNamespacedSecret(Constant.TKEEL_ALARM_NAMESPACE, v1Secret, null, null, null, null);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
        //遍历所有规则
        return 1;
    }

    /**
     * 从字符串中解析并转化为NoticeId
     *
     * @param ids
     * @return
     */
    private List<Long> getNoticeId(String ids) {
        List<Long> noticeIds = new ArrayList<>();
        if (ids.contains(",")) {
            String[] idsArray = ids.split(",");
            for (String id : idsArray) {
                noticeIds.add(Long.valueOf(id));
            }
        } else {
            noticeIds.add(Long.valueOf(ids));
        }
        return noticeIds;
    }

    /**
     * 转base64
     *
     * @param filePath
     * @return
     */
    public static byte[] encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return b;
//            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 规则配置
     *
     * @param rule
     * @return
     */
    public static V1PrometheusRule getRuleConfig(RuleParamVo rule) {
        V1PrometheusRule v1PrometheusRule = new V1PrometheusRule();
        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        Map<String, String> annotations = new HashMap<>();
        annotations.put(Constant.RULE_ANNOTATIONS, "{}");
        Map<String, String> labels = new HashMap<>();
        labels.put("prometheus", "k8s");
        labels.put("role", "alert-rules");
        v1ObjectMeta.setAnnotations(annotations);
        v1ObjectMeta.setLabels(labels);
        v1ObjectMeta.setName(Constant.RULE_NAME_PREFIX + rule.getRuleId());
        v1ObjectMeta.setNamespace(Constant.TKEEL_ALARM_NAMESPACE);

        V1PrometheusRuleSpec v1PrometheusRuleSpec = new V1PrometheusRuleSpec();
        List<V1PrometheusRuleSpecGroups> v1PrometheusRuleSpecGroupsList = new ArrayList<>();
        V1PrometheusRuleSpecGroups v1PrometheusRuleSpecGroups = new V1PrometheusRuleSpecGroups();
        v1PrometheusRuleSpecGroups.setName(Constant.GROUP_NAME);
        List<V1PrometheusRuleSpecRules> v1PrometheusRuleSpecRulesList = new ArrayList<>();
        V1PrometheusRuleSpecRules v1PrometheusRuleSpecRules = new V1PrometheusRuleSpecRules();
        Map<String, String> rulesAnnotations = new HashMap<>();
        rulesAnnotations.put("description", rule.getRuleDesc());

        Map<String, String> rulesLabels = new HashMap<>();
        String status = "";
        if (rule.getAlarmLevel() == 1) {
            status = "紧急告警";
        } else if (rule.getAlarmLevel() == 2) {
            status = "重要告警";
        } else if (rule.getAlarmLevel() == 3) {
            status = "次要告警";
        } else if (rule.getAlarmLevel() == 4) {
            status = "告警提示";
        }
        rulesLabels.put("status", status);
        rulesLabels.put("alarmValue", "{{$value}}");
        rulesLabels.put("tenantId", rule.getTenantId());
        rulesLabels.put("ruleId", String.valueOf(rule.getRuleId()));
        rulesLabels.put("alarmLevel", rule.getAlarmLevel().toString());
        rulesLabels.put("alarmSource", rule.getAlarmSourceObject().toString());
        rulesLabels.put("alarmStrategy", rule.getAlarmRuleType().toString());
        rulesLabels.put("alarmType", rule.getAlarmType().toString());
        rulesLabels.put("objectId", rule.getDeviceId());
        v1PrometheusRuleSpecRules.setAnnotations(rulesAnnotations);
        v1PrometheusRuleSpecRules.setLabels(rulesLabels);
        v1PrometheusRuleSpecRules.setExpr(rule.getPromQl());
        v1PrometheusRuleSpecRules.setAlert(rule.getRuleName()); //
        v1PrometheusRuleSpecRules.setFor(Constant.RULE_FOR);

        v1PrometheusRuleSpecRulesList.add(v1PrometheusRuleSpecRules);
        v1PrometheusRuleSpecGroups.setRules(v1PrometheusRuleSpecRulesList);
        v1PrometheusRuleSpecGroupsList.add(v1PrometheusRuleSpecGroups);
        v1PrometheusRuleSpec.setGroups(v1PrometheusRuleSpecGroupsList);

        v1PrometheusRule.setApiVersion(Constant.RULE_API_VERSION);
        v1PrometheusRule.setKind(Constant.RULE_KIND);
        v1PrometheusRule.setMetadata(v1ObjectMeta);
        v1PrometheusRule.setSpec(v1PrometheusRuleSpec);
        return v1PrometheusRule;
    }

    /**
     * 默认加上系统平台通知配置
     * @return
     */
//    public List<Receiver> setReceiverConfig(){
//
//        List<Receiver> receivers =new ArrayList<>();
//        List<WebhookConfig> webhookConfigList =new ArrayList<>();
//        Receiver prometheusReceiver = new Receiver();
//        Receiver eventReceiver = new Receiver();
//        Receiver auditingReceiver = new Receiver();
//
//
//        HttpConfig httpConfig =new HttpConfig();
//        httpConfig.setFollow_redirects(true);
//
//        WebhookConfig webhookConfig =new WebhookConfig();
//        webhookConfig.setSend_resolved(true);
//        webhookConfig.setMax_alerts(0);
//        webhookConfig.setUrl("http://notification-manager-svc.kubesphere-monitoring-system.svc:19093/api/v2/alerts");
//        webhookConfig.setHttp_config(httpConfig);
//        webhookConfigList.add(webhookConfig);
//
//
//        prometheusReceiver.setName("prometheus");
//        prometheusReceiver.setWebhook_configs(webhookConfigList);
//        eventReceiver.setName("event");
//        eventReceiver.setWebhook_configs(webhookConfigList);
//        auditingReceiver.setName("auditing");
//        auditingReceiver.setWebhook_configs(webhookConfigList);
//
//        receivers.add(prometheusReceiver);
//        receivers.add(eventReceiver);
//        receivers.add(auditingReceiver);
//
//        return  receivers;
//    }

//    public List<Routes> setRoutesConfig(){
//        List<Routes> routesList = new ArrayList<>();
//        Routes prometheusRoutes = new Routes();
//        Routes eventRoutes = new Routes();
//        Routes auditingRoutes = new Routes();
//        Map<String, String> prometheus_map = new HashMap<>();
//        Map<String, String> event_map = new HashMap<>();
//        Map<String, String> auditing_map = new HashMap<>();
//        prometheus_map.put("alerttype",".*");
//        event_map.put("alerttype","event");
//        auditing_map.put("alerttype","auditing");
//
//        prometheusRoutes.setReceiver("prometheus");
//        prometheusRoutes.setMatch_re(prometheus_map);
////        prometheusRoutes.setContinueText(false);
//
//        eventRoutes.setReceiver("event");
//        eventRoutes.setMatch(event_map);
////        eventRoutes.setContinueText(false);
//        eventRoutes.setGroup_interval("30s");
//
//        auditingRoutes.setReceiver("auditing");
//        auditingRoutes.setMatch(auditing_map);
////        auditingRoutes.setContinueText(false);
//        auditingRoutes.setGroup_interval("30s");
//
//        routesList.add(prometheusRoutes);
//        routesList.add(eventRoutes);
//        routesList.add(auditingRoutes);
//
//        return routesList;
//    }
}

