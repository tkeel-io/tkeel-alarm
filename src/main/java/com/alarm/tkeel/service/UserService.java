package com.alarm.tkeel.service;

import com.alarm.tkeel.pojo.User;
import com.alarm.tkeel.utils.PageInfo;

public interface UserService {
    PageInfo<User> findAllUser(int pageNum, int pageSize);
}
