package com.alarm.tkeel.service.impl;

import com.alarm.tkeel.dao.UserMapper;
import com.alarm.tkeel.pojo.User;
import com.alarm.tkeel.service.UserService;
import com.alarm.tkeel.utils.PageInfo;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public PageInfo<User> findAllUser(int pageNum, int pageSize) {
        // 设置分页参数; pageNum:页码, pageSize:每页大小
        PageHelper.startPage(pageNum,pageSize);
        // 执行sql查询方法查询所有数据, 会自动分页
        List<User> list = userMapper.findAllUser();
        return new PageInfo<User>(list);
    }
}
