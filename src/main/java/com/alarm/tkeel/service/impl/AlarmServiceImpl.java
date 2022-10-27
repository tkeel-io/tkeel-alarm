package com.alarm.tkeel.service.impl;

import com.alarm.tkeel.dao.AlarmMapper;
import com.alarm.tkeel.pojo.AlarmHandle;
import com.alarm.tkeel.pojo.AlarmRecord;
import com.alarm.tkeel.pojo.AlarmRecordParamVo;
import com.alarm.tkeel.pojo.rules.EnableParamVo;
import com.alarm.tkeel.service.AlarmService;
import com.alarm.tkeel.service.RuleService;
import com.alarm.tkeel.utils.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Resource
    private AlarmMapper alarmMapper;
    @Autowired
    private RuleService ruleService;


    @Override
    public PageInfo<AlarmRecord> queryAlarmRecord(AlarmRecordParamVo alarmRecordParamVo) {
        // 设置分页参数; pageNum:页码, pageSize:每页大小
        PageHelper.startPage(alarmRecordParamVo.getPageNum(), alarmRecordParamVo.getPageSize());
        // 执行sql查询方法查询所有数据, 会自动分页
        List<AlarmRecord> list = alarmMapper.queryAlarmRecord(alarmRecordParamVo);
        return new PageInfo<AlarmRecord>(list);
    }

    @Override
    public Long countPendingRecord(AlarmRecord alarmRecord) {
        System.out.println("countPendingRecord====" + alarmRecord.getRecordHash());
        return alarmMapper.countPendingRecord(alarmRecord);
    }

    @Override
    public int updateAlarmHandleOpinions(AlarmHandle alarmHandle) {
        alarmHandle.setHandTime(System.currentTimeMillis() / 1000);
        alarmHandle.setAlarmStatus(1);
        int code = alarmMapper.updateAlarmHandleOpinions(alarmHandle);
        if (code > 0) {
            // 告警记录被处理后则停用规则对应的规则。
            EnableParamVo enableParamVo = new EnableParamVo();
            enableParamVo.setEnable(0);
            enableParamVo.setRuleId(alarmHandle.getRuleId());
            ruleService.setEnable(enableParamVo);
            // @TODO 将未关联的告警事件与告警记录关联。
            // 1. 查询Alarm
            // AlarmRecord alarmRecord = alarmMapper.getAlarmRecord(alarmHandle.getAlarmId());
            // 2. 更新所有未关联的告警事件
            //if (alarmRecord != null) {
            //    alarmMapper.updateAlarmEventAlarmID(alarmRecord);
            //}
        }
        return code;
    }

    @Override
    public Long createAlarmRecord(AlarmRecord alarmRecord) {
        return alarmMapper.createAlarmRecord(alarmRecord);
    }
    @Override
    public Long createAlarmEvent(List<AlarmRecord> alarmRecord){
        return alarmMapper.createAlarmEvent(alarmRecord);
    }

    @Override
    public int updateAlarmEventAlarmID(AlarmRecord alarmRecord) {
        if(alarmRecord.getAlarmId() == 0){
            System.out.println("[Error]updateAlarmEventAlarmID AlarmId is empty");
            return -1;
        }
        if(alarmRecord.getRecordHash().isEmpty()){
            System.out.println("[Error]updateAlarmEventAlarmID RecordHash is empty");
            return -1;
        }
        int code = alarmMapper.updateAlarmEventAlarmID(alarmRecord);
        return code;
    }

}
