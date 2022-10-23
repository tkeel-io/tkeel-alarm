package com.alarm.tkeel.controller;

import com.alarm.tkeel.pojo.PortalRuleDesc;
import com.alarm.tkeel.pojo.rules.DeleteParamVo;
import com.alarm.tkeel.pojo.rules.EnableParamVo;
import com.alarm.tkeel.pojo.rules.RuleParamVo;
import com.alarm.tkeel.pojo.rules.UpdateNoticeParamVo;
import com.alarm.tkeel.result.ResultData;
import com.alarm.tkeel.service.RuleService;
import com.alarm.tkeel.utils.EncoderUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/v1")
@Api(value = "规则接口",tags = "规则相关接口", description = "告警规则相关接口")
public class RuleController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RuleService ruleService;

    @Autowired
    private EncoderUtils encoderUtils;


    /**
     * 根据条件查询规则列表
     * @param ruleId
     * @param alarmLevel
     * @param alarmRuleType
     * @param alarmType
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/rule/query")
    @ApiOperation(value = "查询规则列表",notes = "查询规则列表",produces = "application/json")
    public ResultData query(@RequestParam(value = "ruleId",required = false) Long ruleId,
                            @RequestParam(value = "ruleName",required = false) String ruleName,
                            @RequestParam(value = "alarmLevel",required = false) Integer alarmLevel,
                            @RequestParam(value = "alarmRuleType",required = false) Integer alarmRuleType,
                            @RequestParam(value = "alarmType",required = false) Integer alarmType,
                            @RequestParam(value = "enable",required = false) Integer enable,
                            @RequestParam(value = "pageNum",required = false) Integer pageNum,
                            @RequestParam(value = "pageSize",required = false) Integer pageSize,
                            @RequestHeader("x-tKeel-auth") String token){
        System.out.println("/rule/query token ====="+token);
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        String tenantId = encoderUtils.getTenantId(token);
        RuleParamVo ruleParamVo = new RuleParamVo();
        ruleParamVo.setRuleId(ruleId == null ? null : ruleId);
        ruleParamVo.setRuleName(ruleName == null ? null : ruleName);
        ruleParamVo.setTenantId(tenantId == null ? null : tenantId);
        ruleParamVo.setAlarmLevel(alarmLevel == null ? null : alarmLevel);
        ruleParamVo.setAlarmRuleType(alarmRuleType == null ? null : alarmRuleType);
        ruleParamVo.setAlarmType(alarmType == null ? null : alarmType);
        ruleParamVo.setEnable(enable);
        ruleParamVo.setPageNum(pageNum);
        ruleParamVo.setPageSize(pageSize);
        return ResultData.success(ruleService.queryRuleList(ruleParamVo));
    }

    /**
     * 通过规则ID获取描述
     * @param ruleId
     * @return
     */
    @GetMapping("/ruleDesc/query")
    @ApiOperation(value = "查询规则描述",notes = "查询规则描述",produces = "application/json")
    public ResultData queryRuleDesc(@RequestParam(value = "ruleId",required = false) Long ruleId){
        if (ruleId == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","规则ID不能为空！");
        }
        Map<String,Object> map =new HashMap<>();
        map.put("list",ruleService.queryRuleDesc(ruleId));
        return ResultData.success(map);
    }


    /**
     * 创建规则
     * @param ruleParamVo
     * @return
     */
    @PostMapping("/rule/create")
    @ApiOperation(value = "创建规则",notes = "创建规则",produces = "application/json")
    public ResultData createRule(@RequestBody RuleParamVo ruleParamVo,@RequestHeader("x-tKeel-auth") String token){
        System.out.println("/rule/create token ====="+token);
        if (token == null || token.equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","token无效！");
        }
        if(encoderUtils.getTenantId(token).equals("")){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","解析租户ID失败！");
        }
        ruleParamVo.setTenantId(encoderUtils.getTenantId(token));
        if(ruleParamVo.getAlarmLevel() == null || ruleParamVo.getAlarmRuleType() == null
                || ruleParamVo.getAlarmSourceObject() == null || ruleParamVo.getAlarmType() == null
                || (ruleParamVo.getRuleName() == null || ruleParamVo.getRuleName().equals(""))){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数不全！");
        }
        if(ruleParamVo.getAlarmSourceObject() != null && ruleParamVo.getAlarmSourceObject() == 1){
                if(ruleParamVo.getDeviceCondition() == null){
                    return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数不全！");
                }
        }else if(ruleParamVo.getAlarmSourceObject() != null && ruleParamVo.getAlarmSourceObject() == 0){
            if(ruleParamVo.getPlatformRuleList() == null){
                return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数不全！");
            }
        }
        if(ruleParamVo.getRuleId() != null && ruleParamVo.getEnable() == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","规则状态参数不全！");
        }
        return ResultData.success(ruleService.createRule(ruleParamVo));
    }

    /*
        根据设备ID、遥测ID、设备名称获取告警规则信息&通知对象信息
     */
//    @GetMapping("/rule/details")
//    public ResultData getAlarmDetails(@RequestParam(value = "tenantId",required = false) String tenantId,
//                                      @RequestParam(value = "deviceName",required = false) String deviceName,
//                                      @RequestParam(value = "telemetryId",required = false) String telemetryId){
//        return ResultData.success(null);
//    }

    /**
     * 设置告警通知
     * @param updateNoticeParamVo
     * @return
     */
    @PutMapping("/rule/setNotice")
    @ApiOperation(value = "设备告警通知",notes = "设备告警通知",produces = "application/json")
    public ResultData setNotice(@RequestBody UpdateNoticeParamVo updateNoticeParamVo){
        if(updateNoticeParamVo.getRuleId() == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","通知参数不全！");
        }
        return ResultData.success(ruleService.setNotice(updateNoticeParamVo));
    }

    /**
     * 设置启用/停用
     * @param enableParamVo
     * @return
     */
    @PutMapping("/rule/setEnable")
    @ApiOperation(value = "规则启用/停用",notes = "规则启用/停用",produces = "application/json")
    public ResultData setEnable(@RequestBody EnableParamVo enableParamVo){
        if(enableParamVo.getRuleId() == null || enableParamVo.getEnable() == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数错误！");
        }
        // 此处需调用CRD接口设置告警规则配置
        return ResultData.success(ruleService.setEnable(enableParamVo));
    }

    /**
     * 删除规则记录（假删）
     * @param deleteParamVo
     * @return
     */
    @PutMapping("/rule/deleted")
    @ApiOperation(value = "删除规则",notes = "删除规则",produces = "application/json")
    public ResultData deleted(@RequestBody DeleteParamVo deleteParamVo){
        if(deleteParamVo.getRuleId() == null || deleteParamVo.getDeleted() == null){
            return ResultData.fail("io.tkeel.INTERNAL_ERROR","参数错误！");
        }
        return ResultData.success(ruleService.deleteRule(deleteParamVo));
    }

    /**
     * 创建平台告警描述信息
     * @return
     */
    @PostMapping("/platformRule/create")
    @ApiOperation(value = "新增平台告警描述",notes = "新增平台告警描述",produces = "application/json")
    public ResultData createPlatformRule(@RequestBody PortalRuleDesc portalRuleDesc){
        return ResultData.success(ruleService.insertPortalRuleDesc(portalRuleDesc));
    }

    /**
     * 获取平台告警描述信息
     * @return
     */
    @GetMapping("/rule/platform")
    @ApiOperation(value = "查询平台告警描述",notes = "查询平台告警描述",produces = "application/json")
    public ResultData getPlatformRule(){
        Map<String,Object> map = new HashMap<>();
        map.put("list",ruleService.queryPlatformRuleList());
        return ResultData.success(map);
    }

    /**
     * 根据id获取规则详情
     * @return
     */
    @GetMapping("/rule/detail")
    @ApiOperation(value = "查询规则详情",notes = "查询规则详情",produces = "application/json")
    public ResultData getRuleDetails(@RequestParam(value = "ruleId",required = false) Long ruleId){
        return ResultData.success(ruleService.queryRuleById(ruleId));
    }

    /**
     * 根据模板id获取绑定规则数量
     * @return
     */
    @ApiOperation(value = "查询模板是否绑定规则",notes = "查询模板是否绑定规则",produces = "application/json")
    @GetMapping("/rule/countByTempId")
    public ResultData getCountByTempId(@RequestParam(value = "tempId",required = false) String tempId){
        return ResultData.success(ruleService.queryCountByTempId(tempId));
    }

    /**
     * 模板、设备、遥测（属性）变更后的通知
     */
    @ApiOperation(value = "设备模板变更检测",notes = "设备模板变更检测",produces = "application/json")
    @GetMapping("/rule/tempCheck")
    public ResultData tempCheck(){
        return ResultData.success(null);
    }

}
