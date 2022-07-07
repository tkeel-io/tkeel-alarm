package com.alarm.tkeel.utils;

import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/08/15:57
 */
@Component
public class EncoderUtils {

    public String getTenantId(String token) {
        String tenantId = "";
        String url = "";
        // 解码
        byte[] base64decodedBytes = Base64.getDecoder().decode(token);
        try {
            url = new String(base64decodedBytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error :" + e.getMessage());
            e.printStackTrace();
        }
        url = "?" +  url;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            final String charset = "utf-8";
            url = URLDecoder.decode(url, charset);
            if (url.indexOf('?') != -1) {
                final String contents = url.substring(url.indexOf('?') + 1);
                String[] keyValues = contents.split("&");
                for (int i = 0; i < keyValues.length; i++) {
                    String key = keyValues[i].substring(0, keyValues[i].indexOf("="));
                    String value = keyValues[i].substring(keyValues[i].indexOf("=") + 1);
                    map.put(key, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (map != null){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(entry.getKey().equals("tenant")){
                    tenantId = (String) entry.getValue();
                }
            }
        }
        return tenantId;
    }
}
