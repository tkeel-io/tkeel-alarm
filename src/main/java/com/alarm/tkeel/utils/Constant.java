package com.alarm.tkeel.utils;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/20/10:32
 */
public class Constant {
    public static String RULE_NAME_PREFIX = "rule-";
    public static String ALERT_NAME_PREFIX = "alert-";

    public static String RULE_ANNOTATIONS = "kubectl.kubernetes.io/last-applied-configuration";

    public static String TKEEL_ALARM_NAMESPACE = "kubesphere-monitoring-system";

    public static String ALERT_API_VERSION = "monitoring.coreos.com/v1alpha1";
    public static String RULE_API_VERSION = "monitoring.coreos.com/v1";

    public static String RULE_FOR = "5s";

    public static String RULE_KIND = "PrometheusRule";
    public static String ALERT_KIND = "AlertmanagerConfig";

    public static String METRIC_NAMES = "entity_telemetry";

    public static String GROUP_NAME = "tkeel.alarm.rules";
    public static String SECRET_KIND = "Secret";

    public static String ALERT_MANAGER_NAME = "alertmanager-main";
}
