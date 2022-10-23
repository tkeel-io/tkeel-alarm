package com.alarm.tkeel.crd.alertmanager;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import io.fabric8.kubernetes.api.model.KubernetesResource;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/15/16:45
 */
@Data
public class Route{
    private String receiver;
    private String group_wait = "1s";
    private String repeat_interval = "1s";
    private List<Routes> routes = new ArrayList<>();
    private String[] group_by;
    private String group_interval = "1s";

    @SerializedName("continue")
    private Boolean continueText;
}