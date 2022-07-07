package com.alarm.tkeel.crd.alertmanager;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonDeserializer.None;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.fabric8.kubernetes.api.model.KubernetesResource;

import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(
        using = None.class
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "continue", "remainingItemCount", "resourceVersion", "selfLink"})
public class ListMeta implements KubernetesResource {
    @JsonProperty("continue")
    private String _continue;
    @JsonProperty("remainingItemCount")
    private Long remainingItemCount;
    @JsonProperty("resourceVersion")
    private String resourceVersion;
    @JsonProperty("selfLink")
    private String selfLink;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public ListMeta() {
    }

    public ListMeta(String _continue, Long remainingItemCount, String resourceVersion, String selfLink) {
        this._continue = _continue;
        this.remainingItemCount = remainingItemCount;
        this.resourceVersion = resourceVersion;
        this.selfLink = selfLink;
    }

    @JsonProperty("continue")
    public String getContinue() {
        return this._continue;
    }

    @JsonProperty("continue")
    public void setContinue(String _continue) {
        this._continue = _continue;
    }

    @JsonProperty("remainingItemCount")
    public Long getRemainingItemCount() {
        return this.remainingItemCount;
    }

    @JsonProperty("remainingItemCount")
    public void setRemainingItemCount(Long remainingItemCount) {
        this.remainingItemCount = remainingItemCount;
    }

    @JsonProperty("resourceVersion")
    public String getResourceVersion() {
        return this.resourceVersion;
    }

    @JsonProperty("resourceVersion")
    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    @JsonProperty("selfLink")
    public String getSelfLink() {
        return this.selfLink;
    }

    @JsonProperty("selfLink")
    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String toString() {
        return "ListMeta(_continue=" + this.getContinue() + ", remainingItemCount=" + this.getRemainingItemCount() + ", resourceVersion=" + this.getResourceVersion() + ", selfLink=" + this.getSelfLink() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
    protected boolean canEqual(Object other) {
        return other instanceof io.fabric8.kubernetes.api.model.ListMeta;
    }



    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
