package com.alarm.tkeel.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable{
    private Integer id;
    private String username;
    private String password;
    private String name;
}
