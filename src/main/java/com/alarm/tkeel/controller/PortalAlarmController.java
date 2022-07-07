package com.alarm.tkeel.controller;


import com.alarm.tkeel.pojo.PortalAlarmParamVo;
import com.alarm.tkeel.pojo.UpdateStatusParam;
import com.alarm.tkeel.result.ResultData;
import com.alarm.tkeel.service.PortalAlarmService;
import com.alarm.tkeel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Api(value = "门户接口",tags = "门户接口", description = "用于低第三方提供获取告警记录相关接口")
public class PortalAlarmController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private PortalAlarmService portalAlarmService;

    /**
     * 获取告警记录列表
     * @param alarmId
     * @param tenantId
     * @param alarmStatus
     * @param telemetryId
     * @param resultCount
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/alarm/list")
    @ApiOperation(value = "查询告警信息列表",notes = "查询告警信息列表",produces = "application/json")
    public ResultData getList(@RequestParam(value = "alarmId",required = false) String alarmId,
                              @RequestParam(value = "tenantId",required = false) String tenantId,
                              @RequestParam(value = "alarmStatus",required = false) Integer[] alarmStatus,
                              @RequestParam(value = "telemetryId",required = false) String telemetryId,
                              @RequestParam(value = "resultCount",required = false) Integer resultCount,
                              @RequestParam(value = "pageNum",required = false) Integer pageNum,
                              @RequestParam(value = "pageSize",required = false) Integer pageSize){
        PortalAlarmParamVo portalAlarmParamVo =new PortalAlarmParamVo();
        if(resultCount != null && resultCount == 1){
            //仅返回告警总数
            portalAlarmParamVo.setAlarmId(alarmId == null ? null : alarmId);
            portalAlarmParamVo.setAlarmStatus(alarmStatus == null ? null : alarmStatus);
            portalAlarmParamVo.setTelemetryId(telemetryId == null ? null : telemetryId);
            portalAlarmParamVo.setTenantId(tenantId == null ? null : tenantId);
            return ResultData.success(portalAlarmService.resultAlarmRecordCount(portalAlarmParamVo));
        }
        else if(alarmId == null && tenantId == null && alarmStatus == null && telemetryId ==null && resultCount != null && resultCount == 0){
            //返回全量告警列表
            if(pageNum == null || pageSize ==null){
                return ResultData.fail("io.tkeel.INTERNAL_ERROR","分页参数不能为空！");
            }
            return ResultData.success(portalAlarmService.findAllAlarmEventRecord(pageNum,pageSize));
        }else {
            //根据参数进行过滤查询
            if(pageNum == null || pageSize ==null){
                return ResultData.fail("io.tkeel.INTERNAL_ERROR","分页参数不能为空！");
            }
            portalAlarmParamVo.setAlarmId(alarmId == null ? null : alarmId);
            portalAlarmParamVo.setAlarmStatus(alarmStatus == null ? null : alarmStatus);
            portalAlarmParamVo.setTelemetryId(telemetryId == null ? null : telemetryId);
            portalAlarmParamVo.setTenantId(tenantId == null ? null : tenantId);
            return ResultData.success(portalAlarmService.findAlarmEventRecord(pageNum,pageSize,portalAlarmParamVo));
        }
    }

    /**
     * 根据条件设置告警状态
     * @param updateStatusParam
     * @return
     */
    @PostMapping("/alarm/set")
    @ApiOperation(value = "设备告警状态",notes = "设备告警状态",produces = "application/json")
    public ResultData setStatus(@RequestBody UpdateStatusParam updateStatusParam){
        if(updateStatusParam.getTenantId() == null || updateStatusParam.getAlarmEventId() == null || updateStatusParam.getAlarmStatus() == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数不全！");
        }
        int result = portalAlarmService.updateAlarmEventStatus(updateStatusParam);
        if(result > 0){
            return ResultData.success(null);
        }else {
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","更新状态失败！");
        }

    }

    /**
     * 获取告警时间总数
     * @param alarmId
     * @param tenantId
     * @param alarmStatus
     * @param telemetryId
     * @param resultCount
     * @return
     */
    @GetMapping("/alarm/event/count")
    @ApiOperation(value = "查询告警事件总数",notes = "查询告警事件总数",produces = "application/json")
    public ResultData getEventCount(@RequestParam(value = "alarmId",required = false) String alarmId,
                              @RequestParam(value = "tenantId",required = false) String tenantId,
                              @RequestParam(value = "alarmStatus",required = false) Integer[] alarmStatus,
                              @RequestParam(value = "telemetryId",required = false) String telemetryId,
                              @RequestParam(value = "resultCount",required = false) Integer resultCount){
        PortalAlarmParamVo portalAlarmParamVo =new PortalAlarmParamVo();
        if(resultCount != null && resultCount == 1){
            //仅返回告警总数
            portalAlarmParamVo.setAlarmId(alarmId == null ? null : alarmId);
            portalAlarmParamVo.setAlarmStatus(alarmStatus == null ? null : alarmStatus);
            portalAlarmParamVo.setTelemetryId(telemetryId == null ? null : telemetryId);
            portalAlarmParamVo.setTenantId(tenantId == null ? null : tenantId);
            return ResultData.success(portalAlarmService.resultAlarmEventCount(portalAlarmParamVo));
        }
        return ResultData.success(null);
    }

}
