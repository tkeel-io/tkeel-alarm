package com.alarm.tkeel.service;

import io.kubernetes.client.openapi.models.V1Secret;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/22/15:08
 */
public interface K8SService {

    int deleteSecret(String name,String namespace);

    int createSecret(String name, String namespace);
}
