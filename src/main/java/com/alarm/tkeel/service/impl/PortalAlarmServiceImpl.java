package com.alarm.tkeel.service.impl;

import com.alarm.tkeel.dao.PortalAlarmMapper;
import com.alarm.tkeel.pojo.*;
import com.alarm.tkeel.service.PortalAlarmService;
import com.alarm.tkeel.utils.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PortalAlarmServiceImpl implements PortalAlarmService {

    @Resource
    private PortalAlarmMapper portalAlarmMapper;


    @Override
    public PageInfo<PortalAlarm> findAlarmEventRecord(int pageNum, int pageSize,PortalAlarmParamVo portalAlarmParamVo) {
        // 设置分页参数; pageNum:页码, pageSize:每页大小
        PageHelper.startPage(pageNum,pageSize);
        // 执行sql查询方法查询所有数据, 会自动分页
        List<PortalAlarm> list = portalAlarmMapper.findAlarmRecord(portalAlarmParamVo);
        return new PageInfo<PortalAlarm>(list);
    }

    @Override
    public PageInfo<PortalAlarm> findAllAlarmEventRecord(int pageNum, int pageSize) {
        // 设置分页参数; pageNum:页码, pageSize:每页大小
        PageHelper.startPage(pageNum,pageSize);
        // 执行sql查询方法查询所有数据, 会自动分页
        List<PortalAlarm> list = portalAlarmMapper.findAllAlarmRecord();
        return new PageInfo<PortalAlarm>(list);
    }

    @Override
    public AlarmRecordCount resultAlarmRecordCount(PortalAlarmParamVo portalAlarmParamVo) {
        return portalAlarmMapper.resultAlarmRecordCount(portalAlarmParamVo);
    }

    @Override
    public AlarmEventCount resultAlarmEventCount(PortalAlarmParamVo portalAlarmParamVo) {
        return portalAlarmMapper.resultAlarmEventCount(portalAlarmParamVo);
    }

    @Override
    public int updateAlarmEventStatus(UpdateStatusParam updateStatusParam) {
        updateStatusParam.setHandTime(System.currentTimeMillis()/1000);
        return portalAlarmMapper.updateAlarmStatus(updateStatusParam);
    }
}
