/**
 * Copyright 2022 guojun
 */
package com.alarm.tkeel.pojo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class Res implements Serializable {
    @ApiModelProperty(value = "-")
    private int ret;
    @ApiModelProperty(value = "-")
    private String msg;
}
