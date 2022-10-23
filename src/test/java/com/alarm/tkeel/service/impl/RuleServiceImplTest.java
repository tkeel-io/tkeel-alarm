//package com.alarm.tkeel.service.impl;
//
//import com.alarm.tkeel.AlarmApplication;
//import com.alarm.tkeel.pojo.rules.DeviceCondition;
//import com.alarm.tkeel.pojo.rules.RuleParamVo;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @Author guojun
// * @Description 开心工作，快乐生活
// * @Date 2022/05/31/16:56
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = AlarmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class RuleServiceImplTest {
//    @Autowired
//    private RuleServiceImpl ruleService;
//
//    @Test
//    void createRule() {
//        Map<String,String> map =new HashMap<>();
//        map.put("驱动安装失败","xxx{a='222'}");
//        map.put("登录异常","xxx{b='error'}");
//        RuleParamVo ruleParamVo =new RuleParamVo();
//        List<DeviceCondition> deviceConditionList = new ArrayList<>();
//        DeviceCondition deviceCondition =new DeviceCondition();
//        DeviceCondition deviceCondition1 =new DeviceCondition();
//        ruleParamVo.setTenantId("ZQ8mV0rk");
//        ruleParamVo.setAlarmType(1);
//        ruleParamVo.setAlarmRuleType(1);
//        ruleParamVo.setRuleName("规则测试");
//        ruleParamVo.setAlarmLevel(1);
//        ruleParamVo.setAlarmSourceObject(1);
////        ruleParamVo.setTelemetryType(2);
//        ruleParamVo.setPlatformAlarmRule(map);
//        ruleParamVo.setCondition("or");
//        ruleParamVo.setDeviceName("UPS");
//        ruleParamVo.setDeviceId("iotd-11sss-22sss-sssss-ssse");
//        deviceCondition.setTelemetryId("202071003");
//        deviceCondition.setTelemetryName("空调出风温度");
//        deviceCondition.setTime(3);
//        deviceCondition.setPolymerize("max");
//        deviceCondition.setOperator(">");
//        deviceCondition.setValue("28");
//        deviceCondition1.setTelemetryId("202071002");
//        deviceCondition1.setTelemetryName("空调回风温度");
//        deviceCondition1.setTime(5);
//        deviceCondition1.setPolymerize("min");
//        deviceCondition1.setOperator(">");
//        deviceCondition1.setValue("28");
//        deviceConditionList.add(deviceCondition);
//        deviceConditionList.add(deviceCondition1);
//        ruleParamVo.setDeviceCondition(deviceConditionList);
//        ruleService.createRule(ruleParamVo);
//    }
//}