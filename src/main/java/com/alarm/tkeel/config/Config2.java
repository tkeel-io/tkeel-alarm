package com.alarm.tkeel.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config2")
@Data
public class Config2 {
    public String version;
}
