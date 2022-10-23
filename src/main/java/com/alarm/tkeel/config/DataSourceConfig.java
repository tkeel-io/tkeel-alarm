//package com.alarm.tkeel.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
///**
// * @Author guojun
// * @Description 开心工作，快乐生活
// * @Date 2022/07/07/11:20
// */
//@Configuration
//@Primary
//public class DataSourceConfig {
//    @Value("${spring.datasource.url}")
//    private String datasourceUrl;
//    @Value("${spring.datasource.driver-class-name}")
//    private String driverClassName;
//    @Value("${spring.datasource.username}")
//    private String username;
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Bean     //声明其为Bean实例
//    public DataSource dataSource(){
//        DruidDataSource datasource = new DruidDataSource();
//
//        datasource.setUrl(datasourceUrl);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        datasource.setDriverClassName(driverClassName);
//
//        try {
//            Class.forName(driverClassName);
//            String url01 = datasourceUrl.substring(0,datasourceUrl.indexOf("?"));
//            String url02 = url01.substring(0,url01.lastIndexOf("/"));
//            String datasourceName = url01.substring(url01.lastIndexOf("/")+1);
//            // 连接已经存在的数据库，如：mysql
//            Connection connection = DriverManager.getConnection(url02, username, password);
//            Statement statement = connection.createStatement();
//
//            // 创建数据库
//            statement.executeUpdate("create database if not exists `" + datasourceName + "` default character set utf8 COLLATE utf8_general_ci");
//
//            statement.close();
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return datasource;
//    }
//
//}
