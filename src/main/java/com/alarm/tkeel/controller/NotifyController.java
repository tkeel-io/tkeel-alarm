package com.alarm.tkeel.controller;

import com.alarm.tkeel.pojo.notify.*;
import com.alarm.tkeel.pojo.rules.DeviceTemp;
import com.alarm.tkeel.pojo.rules.Rule;
import com.alarm.tkeel.result.ResultData;
import com.alarm.tkeel.service.RuleService;
import com.alarm.tkeel.utils.EncoderUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/v1")
@Api(value = "平台通知",tags = "平台通知", description = "平台通知")
public class NotifyController {

    @Autowired
    private RuleService ruleService;

    @Autowired
    private EncoderUtils encoderUtils;

    @GetMapping("/notifications")
    @ApiOperation(value = "平台通知接口",notes = "平台通知接口",produces = "application/json")
    public ResultData getNotifyInfo(@RequestHeader("x-tKeel-auth") String token){
        System.out.println("/notifications token ====="+token);
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        String tenantId = encoderUtils.getTenantId(token);
        List<Rule> ruleList = ruleService.queryRuleStatus(tenantId);
        if(ruleList == null){
            return ResultData.fail("io.tkeel.ERROR","获取规则状态列表失败！");
        }
        List<Notification> notificationList = new ArrayList<>();
        for(Rule rule : ruleList) {
            UUID uuid = UUID.randomUUID();
            Notification notification = new Notification();
            notification.setId(uuid.toString());
            notification.setTitle("监控告警插件通知");
            if(rule.getTempStatus() > 0){
                notification.setContent("告警策略「"+rule.getRuleName()+"」使用的模板发生变更");
            }
            if(rule.getDeviceStatus() > 0){
                notification.setContent("告警策略「"+rule.getRuleName()+"」使用的设备发生变更");
            }
            if(rule.getTelemetryStatus() > 0){
                notification.setContent("告警策略「"+rule.getRuleName()+"」使用的遥测属性发生变更");
            }
            notification.setCreate_timestamp(System.currentTimeMillis());
            Action action = new Action();
            action.setType("internal-jump");
            action.setValue("/tenant-alarm-policy");
            Extras extras = new Extras();
            extras.setIs_open_in_new_window(false);
            action.setExtras(extras);
            notification.setAction(action);
            notificationList.add(notification);
        }
        return ResultData.success(notificationList);
    }

    @PostMapping("/notify/update")
    @ApiOperation(value = "模板、设备、遥测更新通知接口",notes = "模板、设备、遥测更新通知接口",produces = "application/json")
    public ResultData notifyUpdate(@RequestBody NotifyStatus notifyStatus){
        System.out.println("/notify/update notifyStatus ====="+ JSON.toJSONString(notifyStatus));
        if(notifyStatus == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","无效参数！");
        }
        List<String> listObjectId =new ArrayList<>();
        if(notifyStatus.getType().equals("temp")){
            if(notifyStatus.getTempId()!= null && !notifyStatus.getTempId().equals(""))
            {
                String[] strArr = null;
                if(notifyStatus.getTempId().contains(",")) {
                    strArr= notifyStatus.getTempId().split(",");
                    listObjectId = Arrays.asList(strArr);
                }else {
                    listObjectId.add(notifyStatus.getTempId());
                }
            }
            TempStatus tempStatus =new TempStatus();
            tempStatus.setTempId(listObjectId);
            tempStatus.setTempStatus(notifyStatus.getStatus());
            tempStatus.setTenantId(notifyStatus.getTenantId());
            return ResultData.success(ruleService.updateTempStatus(tempStatus));
        }
        else  if(notifyStatus.getType().equals("device")){
            if(notifyStatus.getDeviceId()!= null && !notifyStatus.getDeviceId().equals(""))
            {
                String[] strArr = null;
                if(notifyStatus.getDeviceId().contains(",")) {
                    strArr= notifyStatus.getDeviceId().split(",");
                    listObjectId = Arrays.asList(strArr);
                }else {
                    listObjectId.add(notifyStatus.getDeviceId());
                }
            }
            DeviceStatus deviceStatus =new DeviceStatus();
            deviceStatus.setDeviceId(listObjectId);
            deviceStatus.setDeviceStatus(notifyStatus.getStatus());
            deviceStatus.setTenantId(notifyStatus.getTenantId());
            return ResultData.success(ruleService.updateDeviceStatus(deviceStatus));
        }
        else  if(notifyStatus.getType().equals("telemetry")){
            if(notifyStatus.getTelemetryId()!= null && !notifyStatus.getTelemetryId().equals(""))
            {
                String[] strArr = null;
                if(notifyStatus.getTelemetryId().contains(",")) {
                    strArr= notifyStatus.getTelemetryId().split(",");
                    listObjectId = Arrays.asList(strArr);
                }else {
                    listObjectId.add(notifyStatus.getTelemetryId());
                }
            }
            TelemetryStatus telemetryStatus =new TelemetryStatus();
            telemetryStatus.setTenantId(notifyStatus.getTenantId());
            telemetryStatus.setDeviceId(notifyStatus.getDeviceId());
            if(notifyStatus.getTempId().equals("") || notifyStatus.getTempId() == null) {
                telemetryStatus.setTempId(null);
            }else {
                telemetryStatus.setTempId(notifyStatus.getTempId());
            }
            telemetryStatus.setTelemetryId(listObjectId);
            telemetryStatus.setTelemetryStatus(notifyStatus.getStatus());
            return ResultData.success(ruleService.updateTelemetryStatus(telemetryStatus));
        }
        return ResultData.success(null);
    }

}
