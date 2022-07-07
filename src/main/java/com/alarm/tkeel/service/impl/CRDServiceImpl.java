package com.alarm.tkeel.service.impl;

import com.alarm.tkeel.crd.alertmanager.AlertmanagerConfig;
import com.alarm.tkeel.crd.alertmanager.AlertmanagerConfigList;
import com.alarm.tkeel.service.CRDService;
import com.coreos.monitoring.models.V1PrometheusRule;
import com.coreos.monitoring.models.V1PrometheusRuleList;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/14/14:30
 */

@Service
public class CRDServiceImpl implements CRDService {

    @Override
    public int createPrometheusRuleCR(V1PrometheusRule v1PrometheusRule) throws ApiException {
        GenericKubernetesApi<V1PrometheusRule, V1PrometheusRuleList> prometheusRuleApi = getPrometheusRuleApi();
        if(prometheusRuleApi == null){
            return 0;
        }
        return prometheusRuleApi.create(v1PrometheusRule).throwsApiException().getHttpStatusCode();
    }

    @Override
    public int updatePrometheusRuleCR(V1PrometheusRule v1PrometheusRule, String nameSpace, String crName) throws ApiException {
        GenericKubernetesApi<V1PrometheusRule, V1PrometheusRuleList> prometheusRuleApi = getPrometheusRuleApi();
        if(prometheusRuleApi == null){
            return 0;
        }
        String resourceVersion = prometheusRuleApi.get(nameSpace,crName).getObject().getMetadata().getResourceVersion();
        System.out.println("resourceVersion === " + resourceVersion);
        v1PrometheusRule.getMetadata().setResourceVersion(resourceVersion);
        return prometheusRuleApi.update(v1PrometheusRule).throwsApiException().getHttpStatusCode();
    }

    @Override
    public int deletePrometheusRuleCR(String nameSpace, String crName) throws ApiException {
        GenericKubernetesApi<V1PrometheusRule, V1PrometheusRuleList> prometheusRuleApi = getPrometheusRuleApi();
        if(prometheusRuleApi == null){
            return 0;
        }
        return prometheusRuleApi.delete(nameSpace,crName).throwsApiException().getHttpStatusCode();
    }

    @Override
    public int createAlertManagerCR(AlertmanagerConfig alertmanagerConfig) throws ApiException {
        GenericKubernetesApi<AlertmanagerConfig, AlertmanagerConfigList> alertManagerApi = getAlertManagerApi();
        if(alertManagerApi == null) {
            return 0;
        }
        return alertManagerApi.create(alertmanagerConfig).throwsApiException().getHttpStatusCode();
    }

    @Override
    public int updateAlertManagerCR(AlertmanagerConfig alertmanagerConfig, String nameSpace, String crName) throws ApiException {
        GenericKubernetesApi<AlertmanagerConfig, AlertmanagerConfigList> alertManagerApi = getAlertManagerApi();
        if(alertManagerApi == null){
            return 0;
        }
        String resourceVersion = alertManagerApi.get(nameSpace,crName).getObject().getMetadata().getResourceVersion();
        System.out.println("resourceVersion === " + resourceVersion);
        alertmanagerConfig.getMetadata().setResourceVersion(resourceVersion);
        return alertManagerApi.update(alertmanagerConfig).throwsApiException().getHttpStatusCode();
    }

    @Override
    public int deleteAlertManagerCR(String nameSpace, String crName) throws ApiException {
        GenericKubernetesApi<AlertmanagerConfig, AlertmanagerConfigList> alertManagerApi = getAlertManagerApi();
        if(alertManagerApi == null){
            return 0;
        }
        return alertManagerApi.delete(nameSpace,crName).throwsApiException().getHttpStatusCode();
    }

    @Override
    public String getResourceVersion(String nameSpace, String crName) {
        return null;
    }

    /**
     * 构造 prometheusRuleApi 对象
     * @return
     * @throws IOException
     */
    private GenericKubernetesApi<V1PrometheusRule, V1PrometheusRuleList> getPrometheusRuleApi(){
        GenericKubernetesApi<V1PrometheusRule, V1PrometheusRuleList> prometheusRuleApi =
                null;
        try {
            prometheusRuleApi = new GenericKubernetesApi<>(
                    V1PrometheusRule.class,
                    V1PrometheusRuleList.class,
                    "monitoring.coreos.com",
                    "v1",
                    "prometheusrules",
                    ClientBuilder.defaultClient());
        } catch (IOException e) {
            System.out.println("prometheusRuleApi:"+e.getMessage());
            e.printStackTrace();
        }
        return prometheusRuleApi;
    }

    /**
     * 构造 alertManagerApi 对象
     * @return
     * @throws IOException
     */
    private GenericKubernetesApi<AlertmanagerConfig, AlertmanagerConfigList> getAlertManagerApi(){
        GenericKubernetesApi<AlertmanagerConfig, AlertmanagerConfigList> alertManagerApi =
                null;
        try {
            alertManagerApi = new GenericKubernetesApi<>(
                    AlertmanagerConfig.class,
                    AlertmanagerConfigList.class,
                    "monitoring.coreos.com",
                    "v1alpha1",
                    "alertmanagerconfigs",
                    ClientBuilder.defaultClient());
        } catch (IOException e) {
            System.out.println("alertManagerApi:" + e.getMessage());
            e.printStackTrace();
        }
        return alertManagerApi;
    }
}
