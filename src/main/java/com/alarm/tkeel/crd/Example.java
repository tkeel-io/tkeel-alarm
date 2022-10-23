package com.alarm.tkeel.crd;

import com.alarm.tkeel.crd.alertmanager.*;
import com.alibaba.fastjson.JSON;
import com.coreos.monitoring.models.*;
//import io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigSpec;
//import io.fabric8.openshift.api.model.monitoring.v1.Alertmanager;
//import io.fabric8.openshift.api.model.monitoring.v1.AlertmanageranagerList;
//import io.fabric8.openshift.api.model.monitoring.v1alpha1.EmailConfig;
//import io.fabric8.openshift.api.model.monitoring.v1alpha1.Receiver;
//import io.fabric8.openshift.api.model.monitoring.v1alpha1.Route;
import io.fabric8.openshift.api.model.monitoring.v1alpha1.WebhookConfig;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.ApiResponse;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Secret;
import io.kubernetes.client.openapi.models.V1Status;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.Config;
import io.kubernetes.client.util.generic.GenericKubernetesApi;
import org.ho.yaml.Yaml;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/10/14:45
 */
public class Example {
    public static void main(String[] args) throws IOException, ApiException {

        GenericKubernetesApi<V1PrometheusRule, V1PrometheusRuleList> prometheusApi =
                new GenericKubernetesApi<>(
                        V1PrometheusRule.class,
                        V1PrometheusRuleList.class,
                        "monitoring.coreos.com",
                        "v1",
                        "prometheusrules",
                        ClientBuilder.defaultClient());

//
//        // 获取cr的resourceVersion版本号（全局唯一），更新时需要用。
//        System.out.println("getResourceVersion===="+prometheusApi.get("kubesphere-monitoring-system","tkeel-alarm").getObject().getMetadata().getResourceVersion());
//        String version = prometheusApi.get("kubesphere-monitoring-system","tkeel-alarm").getObject().getMetadata().getResourceVersion();
//
        String resourceVersion = prometheusApi.get("kubesphere-monitoring-system","rule-10140").getObject().getMetadata().getResourceVersion();
        V1PrometheusRule v1PrometheusRule = new V1PrometheusRule();
        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
        Map<String,String> annotations =new HashMap<>();
        annotations.put("kubectl.kubernetes.io/last-applied-configuration","{}");
        Map<String,String> labels =new HashMap<>();
        labels.put("prometheus","k8s");
        labels.put("role","alert-rules");
        v1ObjectMeta.setAnnotations(annotations);
        v1ObjectMeta.setLabels(labels);
        v1ObjectMeta.setName("rule-10140");
        v1ObjectMeta.setNamespace("kubesphere-monitoring-system");
        v1ObjectMeta.setResourceVersion(resourceVersion);

        V1PrometheusRuleSpec v1PrometheusRuleSpec =new V1PrometheusRuleSpec();
        List<V1PrometheusRuleSpecGroups> v1PrometheusRuleSpecGroupsList =new ArrayList<>();
        V1PrometheusRuleSpecGroups v1PrometheusRuleSpecGroups = new V1PrometheusRuleSpecGroups();
        v1PrometheusRuleSpecGroups.setName("AAA");
        List<V1PrometheusRuleSpecRules> v1PrometheusRuleSpecRulesList =new ArrayList<>();
        V1PrometheusRuleSpecRules v1PrometheusRuleSpecRules =new V1PrometheusRuleSpecRules();
        Map<String,String> rulesAnnotations =new HashMap<>();
        rulesAnnotations.put("description","{{$labels.mountpoint}} 空调温度过高！");
        rulesAnnotations.put("summary","{{$labels.mountpoint }} 空调温度大于20°(目前使用:{{$value}}%)");

        Map<String,String> rulesLabels =new HashMap<>();
        rulesLabels.put("status","一般告警");
        rulesLabels.put("alarmValue","{{$value}}");
        rulesLabels.put("ruleId","1001511111");
        v1PrometheusRuleSpecRules.setAnnotations(rulesAnnotations);
        v1PrometheusRuleSpecRules.setLabels(rulesLabels);
        v1PrometheusRuleSpecRules.setExpr("ZQ8mV0rk_202071003{tenantId='ZQ8mV0rk'} > 20");
        v1PrometheusRuleSpecRules.setAlert("空调温度");
        v1PrometheusRuleSpecRules.setFor("15s");

        v1PrometheusRule.setApiVersion("monitoring.coreos.com/v1");
        v1PrometheusRule.setKind("PrometheusRule");
        v1PrometheusRule.setMetadata(v1ObjectMeta);
        v1PrometheusRule.setSpec(v1PrometheusRuleSpec);


        v1PrometheusRuleSpecRulesList.add(v1PrometheusRuleSpecRules);
        v1PrometheusRuleSpecGroups.setRules(v1PrometheusRuleSpecRulesList);

        v1PrometheusRuleSpecGroupsList.add(v1PrometheusRuleSpecGroups);
        v1PrometheusRuleSpec.setGroups(v1PrometheusRuleSpecGroupsList);




        System.out.println("resourceVersion === " + resourceVersion);
        v1PrometheusRule.getMetadata().setResourceVersion(resourceVersion);
        int code  = prometheusApi.update(v1PrometheusRule).throwsApiException().getHttpStatusCode();
        System.out.println(code);
//
//
        GenericKubernetesApi<AlertmanagerConfig, AlertmanagerConfigList> alertmanagerConfig1 =
                new GenericKubernetesApi<>(
                        AlertmanagerConfig.class,
                        AlertmanagerConfigList.class,
                        "monitoring.coreos.com",
                        "v1alpha1",
                        "alertmanagerconfigs",
                        ClientBuilder.defaultClient());

        GenericKubernetesApi<Alertmanager, AlertmanagerList> alertmanager =
                new GenericKubernetesApi<>(
                        Alertmanager.class,
                        AlertmanagerList.class,
                        "monitoring.coreos.com",
                        "v1",
                        "alertmanagers",
                        ClientBuilder.defaultClient());

//        String version = alertmanager.get("kubesphere-monitoring-system","alert-config").getObject().getMetadata().getResourceVersion();
//        System.out.println("getResourceVersion===="+ version);


//        AlertmanagerConfig alertmanagerConfig =new AlertmanagerConfig();
//        alertmanagerConfig.setKind("AlertmanagerConfig");
//        alertmanagerConfig.setApiVersion("monitoring.coreos.com/v1alpha1");

//        V1ObjectMeta objectMeta =new V1ObjectMeta();
//        objectMeta.setNamespace("kubesphere-monitoring-system");
//        objectMeta.setName("alert-10133");
//        Map<String,String> label =new HashMap<>();
////        label.put("alertmanagerConfig","example");
//        objectMeta.setLabels(label);
//        objectMeta.setResourceVersion(version);

//        AlertmanagerConfigSpec alertmanagerConfigSpec =new AlertmanagerConfigSpec();
//        List<Receiver> receiverList = new ArrayList<>();
//        List<EmailConfig> emailConfigList = new ArrayList<>();
//        Receiver receiver = new Receiver();
//        receiver.setName("ccc");
//
//        EmailConfig emailConfig = new EmailConfig();
//        WebhookConfig webhookConfig = new WebhookConfig();
//        webhookConfig.setSendResolved(true);
//        webhookConfig.setUrl("http://tkeel-alarm:7878/webhook/demo");
//        emailConfig.setSmarthost("smtp.yunify.com:25");
//        emailConfig.setRequireTLS(false);
//        emailConfig.setFrom("guojun@yunify.com");
//        emailConfig.setAuthUsername("guojun");
//        SecretKeySelector keySelector =new SecretKeySelector();
//        keySelector.setKey("GUOJUNhao923@");
//        keySelector.setName("mysecret");
//        keySelector.setOptional(false);
//        emailConfig.setAuthPassword(keySelector);
//        emailConfig.setTo("362903037@qq.com");
//        emailConfig.setSend_resolved(true);
//        emailConfigList.add(emailConfig);
////        receiver.setEmailConfigs(emailConfigList);
////        List<WebhookConfig> webhookConfigsList =new ArrayList<>();
////        webhookConfigsList.add(webhookConfig);
////        receiver.setWebhookConfigs(webhookConfigsList);
//        receiver.setEmail_configs(emailConfigList);
//        receiverList.add(receiver);
//
//        List<String> list = new ArrayList<>();
//        list.add("rule_id");
//        Route route = new Route();
//        route.setReceiver("ccc");
//
////        route.setGroupInterval("60s");
////        route.setRepeatInterval("60s");
////        route.setGroupWait("60s");
////        route.setGroupBy(list);
//        Routes routes = new Routes();
//        List<Routes> routesList = new ArrayList<>();
//
//        routes.setReceiver("ccc");
//        Map<String, String> match_map = new HashMap<>();
//        match_map.put("tenant_id", "tl1wsgmH");
//        match_map.put("rule_id", "10133");
//        routes.setMatch(match_map);
//        routesList.add(routes);
//        route.setRoutes(routesList);


//        alertmanagerConfigSpec.setReceivers(receiverList);
//        alertmanagerConfigSpec.setRoute(route);
//        alertmanagerConfig.setMetadata(objectMeta);
//        alertmanagerConfig.setSpec(alertmanagerConfigSpec);

        // 创建cr
//        int code = alertmanagerConfig1.create(alertmanagerConfig).throwsApiException().getHttpStatusCode();
//        System.out.println(code);


//        ApiClient client = Config.defaultClient();
//        Configuration.setDefaultApiClient(client);
//        Global global = new Global();
//        global.setResolve_timeout("20m");
//        global.setSmtp_smarthost("smtp.yunify.com:25");
//        global.setSmtp_auth_username("guojun");
//        global.setSmtp_auth_password("GUOJUNhao923@");
//        global.setSmtp_from("guojun@yunify.com");
//        global.setSmtp_require_tls(false);
//        CoreV1Api api = new CoreV1Api();
//        V1Secret v1Secret = new V1Secret();
//        v1Secret.setApiVersion("v1");
//        v1Secret.setKind("Secret");
//        V1ObjectMeta v1ObjectMeta = new V1ObjectMeta();
//        v1ObjectMeta.setNamespace("kubesphere-monitoring-system");
//        v1ObjectMeta.setName("alertmanager-main");
//        v1ObjectMeta.setAnnotations(null);
//        v1Secret.setMetadata(v1ObjectMeta);
//        v1Secret.setType("Opaque");
//        Map<String, byte[]> map = new HashMap<>();
//
//        AlertEntity alertEntity = new AlertEntity();
//        alertEntity.setGlobal(global);
//        alertEntity.setRoute(route);
//        alertEntity.setReceivers(receiverList);
//        byte[] bytes;
//        ByteArrayOutputStream bo = new ByteArrayOutputStream();
//        ObjectOutputStream oo = new ObjectOutputStream(bo);
//        System.out.println(JSON.toJSONString(alertEntity));
//        oo.writeBytes(JSON.toJSONString(alertEntity));
//        bytes = bo.toByteArray();
//        bo.close();
//        oo.close();
//        Map<String, byte[]> data = new HashMap<>();
//        data.put("alertmanager.yaml", bytes);
////        String Base64Str = Convert.ToBase64String(bytes);
//        v1Secret.setData(map);
//        File dumpFile = new File(System.getProperty("user.dir") + "/alertmanager.yaml");
//        try {
//            Yaml.dump(alertEntity, dumpFile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        byte[] be = encryptToBase64(System.getProperty("user.dir") + "/alertmanager.yaml");
//        map.put("alertmanager.yaml",be);
////        Call call = api.connectGetNamespacedPodExecCall("alertmanager-main-0","kubesphere-monitoring-system","kubectl create secret generic alertmanager-main --from-file=alertmanager.yaml",null,null,null,null,null,null);
////        v1Secret.setStringData(map);
//        ApiResponse<V1Status> apiResponse = api.deleteNamespacedSecretWithHttpInfo("alertmanager-main","kubesphere-monitoring-system",null,null,null,null,null,null);
//        System.out.println("v1Status == "+ apiResponse.getStatusCode());
//        V1Secret secret = api.createNamespacedSecret("kubesphere-monitoring-system",v1Secret,null,null,null,null);
//        Alertmanager alertmanager1 =new Alertmanager();
//        alertmanager1.setApiVersion("monitoring.coreos.com/v1");
//        V1ObjectMeta objectMeta1=new V1ObjectMeta();
//        objectMeta1.setName("example");
//        objectMeta1.setNamespace("kubesphere-monitoring-system");
//        alertmanager1.setMetadata(objectMeta1);
//        alertmanager1.setKind("Alertmanager");
//        AlertmanagerSpec alertmanagerSpec =new AlertmanagerSpec();
//        LabelSelector labelSelector =new LabelSelector();
//        Map<String,String> map =new HashMap<>();
//        map.put("alertmanagerConfig","example");
//        labelSelector.setMatchLabels(map);
//        alertmanagerSpec.setAlertmanagerConfigSelector(labelSelector);
//        alertmanager1.setSpec(alertmanagerSpec);
//        // 更新cr
//        int code  =  alertmanager.create(alertmanager1).getHttpStatusCode();
//        System.out.println(JSON.toJSONString(secret));
        //删除cr
//        int code  = alertmanager.delete("kubesphere-monitoring-system","alert-config").throwsApiException().getHttpStatusCode();
//        System.out.println(secret);
        // 以上增删改查 均测试通过，封装方法。
    }

    public static byte[] encryptToBase64(String filePath) {
        if (filePath == null) {
            return null;
        }
        try {
            byte[] b = Files.readAllBytes(Paths.get(filePath));
            return b;
//            return Base64.getEncoder().encodeToString(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}