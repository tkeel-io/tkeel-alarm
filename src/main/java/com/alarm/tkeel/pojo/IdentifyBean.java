package com.alarm.tkeel.pojo;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IdentifyBean implements Serializable {
    private Res res;
    private String plugin_id;
    private String version;
    private String tKeel_version;
    private List<AddonsPoints> addons_points;
    private List<ImplementedPlugin> implemented_plugin;
}
