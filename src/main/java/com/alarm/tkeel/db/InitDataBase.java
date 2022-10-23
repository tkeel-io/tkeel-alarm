//package com.alarm.tkeel.db;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.sql.DataSource;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//
///**
// * @Author guojun
// * @Description 开心工作，快乐生活
// * @Date 2022/06/14/16:59
// */
//@Component
//public class InitDataBase {
//
//    @Autowired
//    DataSource dataSource;
//
//    /**
//     * 初始化监控告警数据库
//     */
//    @PostConstruct
//    public void init() {
//        SimpleDateFormat sdf = new SimpleDateFormat();// 格式化时间
//        sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");// a为am/pm的标记
//        Date date = new Date();// 获取当前时间
//        // 项目启动初始化基本数据
//        System.out.println("数据初始化开始:"+ sdf.format(date));
//        // 通过直接读取sql文件执行
//        ClassPathResource resources_db = new ClassPathResource("sql/InitDB.sql");
//        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
//        resourceDatabasePopulator.addScripts(resources_db);
//        resourceDatabasePopulator.execute(dataSource);
//
//        ClassPathResource resources_alarm = new ClassPathResource("sql/alarmInit.sql");
//        ResourceDatabasePopulator resourceDatabasePopulator_alarm = new ResourceDatabasePopulator();
//        resourceDatabasePopulator_alarm.addScripts(resources_alarm);
//        resourceDatabasePopulator_alarm.execute(dataSource);
//
//        Date date2 = new Date();// 获取当前时间
//        System.out.println("数据初始化结束:"+ sdf.format(date2));
//    }
//}
