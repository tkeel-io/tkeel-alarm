package com.alarm.tkeel.controller;


import com.alarm.tkeel.pojo.AlarmHandle;
import com.alarm.tkeel.pojo.AlarmRecordParamVo;
import com.alarm.tkeel.result.ResultData;
import com.alarm.tkeel.service.AlarmService;
import com.alarm.tkeel.service.UserService;
import com.alarm.tkeel.utils.EncoderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
@Api(value = "告警接口",tags = "告警相关接口", description = "告警记录相关接口")
public class AlarmController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserService userService;

    @Autowired
    private AlarmService alarmService;

    @Autowired
    private EncoderUtils encoderUtils;

    /**
     * 获取告警记录列表
     * @param alarmId
     * @param alarmType
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/alarm/query")
    @ApiOperation(value = "获取告警列表",notes = "获取告警列表",produces = "application/json")
    public ResultData getList(@RequestParam(value = "alarmId",required = false) String alarmId,
                              @RequestParam(value = "alarmLevel",required = false) Integer alarmLevel,
                              @RequestParam(value = "alarmSource",required = false) Integer alarmSource,
                              @RequestParam(value = "alarmStrategy",required = false) Integer alarmStrategy,
                              @RequestParam(value = "alarmStatus",required = false) Integer alarmStatus,
                              @RequestParam(value = "alarmType",required = false) Integer alarmType,
                              @RequestParam(value = "startTime",required = false) Long startTime,
                              @RequestParam(value = "endTime",required = false) Long endTime,
                              @RequestParam(value = "pageNum",required = false) Integer pageNum,
                              @RequestParam(value = "pageSize",required = false) Integer pageSize,
                              @RequestHeader("x-tKeel-auth") String token){
        System.out.println("/alarm/query token===="+token);
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        //根据参数进行过滤查询
        if(pageNum == null || pageSize ==null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","分页参数不能为空！");
        }
        String tenantId = encoderUtils.getTenantId(token);
        AlarmRecordParamVo alarmRecordParamVo =new AlarmRecordParamVo();
        alarmRecordParamVo.setAlarmId(alarmId == null ? null : alarmId);
        alarmRecordParamVo.setAlarmLevel(alarmLevel == null ? null : alarmLevel);
        alarmRecordParamVo.setAlarmSource(alarmSource == null ? null : alarmSource);
        alarmRecordParamVo.setAlarmStrategy(alarmStrategy == null ? null : alarmStrategy);
        alarmRecordParamVo.setTenantId(tenantId == null ? null : tenantId);
        alarmRecordParamVo.setAlarmType(alarmType == null ? null : alarmType);
        alarmRecordParamVo.setAlarmStatus(alarmStatus == null ? null : alarmStatus);
        alarmRecordParamVo.setStartTime(startTime == null ? null : startTime);
        alarmRecordParamVo.setEndTime(endTime == null ? null : endTime);
        alarmRecordParamVo.setPageNum(pageNum);
        alarmRecordParamVo.setPageSize(pageSize);
        return ResultData.success(alarmService.queryAlarmRecord(alarmRecordParamVo));

    }

    /**
     * 设置处理意见
     * @param alarmHandle
     * @return
     */
    @PutMapping("/alarm/handle")
    @ApiOperation(value = "设置处理意见",notes = "设置处理意见",produces = "application/json")
    public ResultData setHandleOpinions(@RequestBody AlarmHandle alarmHandle){
        if(alarmHandle.getAlarmId() == null || alarmHandle.getHandOpinions() == null || alarmHandle.getHandOpinions().equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数不全！");
        }
        int result = alarmService.updateAlarmHandleOpinions(alarmHandle);
        if(result > 0){
            return ResultData.success(result);
        }else {
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","更新失败！");
        }
    }
}
