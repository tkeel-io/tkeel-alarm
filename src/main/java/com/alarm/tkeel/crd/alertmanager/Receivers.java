package com.alarm.tkeel.crd.alertmanager;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;


/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/15/16:46
 */
public class Receivers {

    public Receivers() {

    }

    public Receivers(String name) {
        this.name = name;
    }

    private String name;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Receivers name(String name) {
        this.name = name;
        return this;
    }

    private List<WebhookConfig> webhookConfigs = new ArrayList<>();

    @JsonProperty("webhook_configs")
    public List<WebhookConfig> getWebhookConfigs() {
        return webhookConfigs;
    }

    @JsonProperty("webhook_configs")
    public void setWebhookConfigs(List<WebhookConfig> webhookConfigs) {
        this.webhookConfigs = webhookConfigs;
    }

    public Receivers webhookConfigs(List<WebhookConfig> webhookConfigs) {
        this.webhookConfigs = webhookConfigs;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Receivers)) return false;
        Receivers receivers = (Receivers) o;
        return Objects.equals(getName(), receivers.getName()) &&
                Objects.equals(getWebhookConfigs(), receivers.getWebhookConfigs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getWebhookConfigs());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Receivers.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("webhookConfigs=" + webhookConfigs)
                .toString();
    }

}
