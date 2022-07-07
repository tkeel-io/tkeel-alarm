package com.alarm.tkeel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Random;

@EnableScheduling  //扫描定时器
@SpringBootApplication
@MapperScan(basePackages = "com.alarm.tkeel.dao")
public class AlarmApplication implements WebMvcConfigurer {



//    @Bean
//    public MetricInterceptor metricInterceptor() {
//        return new MetricInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(metricInterceptor()).addPathPatterns("/**");
//    }


    public static void main(String[] args) {
        SpringApplication.run(AlarmApplication.class,args);
    }
}
