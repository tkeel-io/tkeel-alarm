package com.alarm.tkeel.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "webhook")
@Data
public class WebHook {
    public String url;
}
