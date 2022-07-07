package com.alarm.tkeel.controller;

import com.alarm.tkeel.pojo.notify.*;
import com.alarm.tkeel.pojo.rules.DeviceTemp;
import com.alarm.tkeel.result.ResultData;
import com.alarm.tkeel.service.RuleService;
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

    @GetMapping("/notifications")
    @ApiOperation(value = "平台通知接口",notes = "平台通知接口",produces = "application/json")
    public ResultData getNotifyInfo(){
        UUID uuid = UUID.randomUUID();
        Notification notification =new Notification();
        notification.setId(uuid.toString());
        notification.setTitle("监控告警插件通知");
        notification.setContent("设备信息变更通知");
        notification.setCreate_timestamp(System.currentTimeMillis());
        Action action =new Action();
        action.setType("internal-jump");
        action.setValue("/tenant-alarm-policy");
        Extras extras =new Extras();
        extras.setIs_open_in_new_window(false);
        action.setExtras(extras);
        notification.setAction(action);
        return ResultData.success(notification);
    }

    @PostMapping("/notify/update")
    @ApiOperation(value = "模板、设备、遥测更新通知接口",notes = "模板、设备、遥测更新通知接口",produces = "application/json")
    public ResultData notifyUpdate(@RequestBody NotifyStatus notifyStatus){
        System.out.println("/notify/update notifyStatus ====="+ JSON.toJSONString(notifyStatus));
        if(notifyStatus == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","无效参数！");
        }
        List<String> listObjectId =new ArrayList<>();
        String[] strArr = null;
        if(notifyStatus.getObjectId()!= null && !notifyStatus.getObjectId().equals(""))
        {
            if(notifyStatus.getObjectId().contains(",")) {
                strArr= notifyStatus.getObjectId().split(",");
                listObjectId = Arrays.asList(strArr);
            }else {
                listObjectId.add(notifyStatus.getObjectId());
            }
        }
        if(notifyStatus.getType().equals("temp")){
            TempStatus tempStatus =new TempStatus();
            tempStatus.setTempId(listObjectId);
            tempStatus.setTempStatus(notifyStatus.getStatus());
            tempStatus.setTenantId(notifyStatus.getTenantId());
            return ResultData.success(ruleService.updateTempStatus(tempStatus));
        }
        else  if(notifyStatus.getType().equals("device")){
            DeviceStatus deviceStatus =new DeviceStatus();
            deviceStatus.setDeviceId(listObjectId);
            deviceStatus.setDeviceStatus(notifyStatus.getStatus());
            deviceStatus.setTenantId(notifyStatus.getTenantId());
            return ResultData.success(ruleService.updateDeviceStatus(deviceStatus));
        }
        else  if(notifyStatus.getType().equals("telemetry")){
            TelemetryStatus telemetryStatus =new TelemetryStatus();
            telemetryStatus.setTenantId(notifyStatus.getTenantId());
            telemetryStatus.setTelemetryId(listObjectId);
            telemetryStatus.setTelemetryStatus(notifyStatus.getStatus());
            return ResultData.success(ruleService.updateTelemetryStatus(telemetryStatus));
        }
        return ResultData.success(null);
    }

}
