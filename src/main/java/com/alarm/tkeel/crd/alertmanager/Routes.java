package com.alarm.tkeel.crd.alertmanager;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/15/16:45
 */
@Data
public class Routes {

    public Routes() {

    }

    private Map<String, String> match = new HashMap<>();
    private String receiver;
    private String[] group_by;

    @JsonProperty("match")
    public Map<String, String> getMatch() {
        return match;
    }

    @JsonProperty("match")
    public void setMatch(Map<String, String> matchRe) {
        this.match = matchRe;
    }

    public Routes match(Map<String, String> matchRe) {
        this.match = matchRe;
        return this;
    }

    @JsonProperty("receiver")
    public String getReceiver() {
        return receiver;
    }

    @JsonProperty("receiver")
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Routes receiver(String receiver) {
        this.receiver = receiver;
        return this;
    }
}
