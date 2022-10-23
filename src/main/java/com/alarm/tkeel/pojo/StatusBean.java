package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class StatusBean implements Serializable {
    private Res res;
    private int status;
}
