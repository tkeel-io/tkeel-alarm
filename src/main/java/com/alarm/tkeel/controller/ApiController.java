package com.alarm.tkeel.controller;

import com.alarm.tkeel.config.Config2;
import com.alarm.tkeel.pojo.*;
import com.alarm.tkeel.service.UserService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Api(value = "平台接口",tags = "平台相关接口", description = "平台插件相关接口")
public class ApiController {

    @Autowired
    private UserService userService;

    @Value("${config2.version}")
    private String version;

    @Autowired
    private Config2 config2;

    @GetMapping("/identify")
    @ApiOperation(value = "获取认证信息",notes = "获取认证信息",produces = "application/json")
    public IdentifyBean getIdentify(){
        List<AddonsPoints> pointsList = new ArrayList<>();
        Res res =new Res();
        res.setRet(0);
        res.setMsg("ok");
        AddonsPoints addonsPoints =new AddonsPoints();
        addonsPoints.setAddons_point("tkeel-alarm");
        addonsPoints.setDesc("tKeel监控告警插件");
        pointsList.add(addonsPoints);
        Addons addons =new Addons();
        addons.setAddons_point("device-schema-change");
        addons.setImplemented_endpoint("v1/notify/update");
        Plugin plugin =new Plugin();
        plugin.setVersion("v1.0.0");
        plugin.setId("tkeel-device");
        List<Addons> addonsList =new ArrayList<>();
        addonsList.add(addons);
        ImplementedPlugin implementedPlugin = new ImplementedPlugin();
        implementedPlugin.setPlugin(plugin);
        implementedPlugin.setAddons(addonsList);
        List<ImplementedPlugin> implementedPluginList =new ArrayList<>();
        implementedPluginList.add(implementedPlugin);
        IdentifyBean identifyBean =new IdentifyBean();
        identifyBean.setPlugin_id("tkeel-alarm");
        identifyBean.setVersion("v1.0.0");
        identifyBean.setTKeel_version("v0.4.0");
        identifyBean.setAddons_points(pointsList);
        identifyBean.setRes(res);
        identifyBean.setImplemented_plugin(implementedPluginList);
        return identifyBean;
    }

    @GetMapping("/status")
    @ApiOperation(value = "获取状态信息",notes = "获取状态信息",produces = "application/json")
    public StatusBean getStatus(){
        Res res = new Res();
        res.setRet(0);
        res.setMsg("ok");
        StatusBean statusBean = new StatusBean();
        statusBean.setRes(res);
        statusBean.setStatus(3);
        return statusBean;
    }

    @GetMapping("/tenant/disable")
    @ApiOperation(value = "租户停用",notes = "租户停用",produces = "application/json")
    public DisableBean getDisable(){
        Res res = new Res();
        res.setRet(0);
        res.setMsg("ok");
        DisableBean disableBean =new DisableBean();
        disableBean.setRes(res);
        return disableBean;
    }

    @GetMapping("/tenant/enable")
    @ApiOperation(value = "租户启用",notes = "租户启用",produces = "application/json")
    public DisableBean getEnable(){
        Res res = new Res();
        res.setRet(0);
        res.setMsg("ok");
        DisableBean disableBean =new DisableBean();
        disableBean.setRes(res);
        return disableBean;
    }

//    @GetMapping("/test")
//    public String getTest(){
//        return JSON.toJSONString(config2.version);
//    }

}
