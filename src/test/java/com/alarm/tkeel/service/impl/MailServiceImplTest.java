//package com.alarm.tkeel.service.impl;
//
//import com.alarm.tkeel.AlarmApplication;
//import com.alibaba.fastjson.JSON;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// * @Author guojun
// * @Description 开心工作，快乐生活
// * @Date 2022/06/02/10:36
// */
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = AlarmApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class MailServiceImplTest {
//
//    @Autowired
//    private MailServiceImpl mailService;
//
//    @Test
//    void queryNoticeGroupByIds() {
//        List<Long>  list = new ArrayList<>();
//        list.add(101L);
//        list.add(102L);
//        System.out.println(JSON.toJSON(mailService.queryNoticeGroupByIds(list)));
//    }
//}