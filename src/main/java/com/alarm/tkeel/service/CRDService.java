package com.alarm.tkeel.service;

import com.alarm.tkeel.crd.alertmanager.AlertmanagerConfig;
import com.coreos.monitoring.models.V1PrometheusRule;
import io.kubernetes.client.openapi.ApiException;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/14/10:38
 */
public interface CRDService {

    int createPrometheusRuleCR(V1PrometheusRule v1PrometheusRule) throws ApiException;

    int updatePrometheusRuleCR(V1PrometheusRule v1PrometheusRule,String nameSpace,String crName) throws ApiException;

    int deletePrometheusRuleCR(String nameSpace,String crName) throws ApiException;

    int createAlertManagerCR(AlertmanagerConfig alertmanagerConfig) throws ApiException;

    int updateAlertManagerCR(AlertmanagerConfig alertmanagerConfig,String nameSpace,String crName) throws ApiException;

    int deleteAlertManagerCR(String nameSpace,String crName) throws ApiException;

    String getResourceVersion(String nameSpace,String crName);
}
