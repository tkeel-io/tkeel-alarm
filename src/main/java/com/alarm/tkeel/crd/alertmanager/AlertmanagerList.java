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
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.ListMeta;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import io.fabric8.openshift.api.model.monitoring.v1.Alertmanager;
import io.kubernetes.client.common.KubernetesListObject;
import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.models.V1ListMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
        using = None.class
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "items"})
@Version("v1")
@Group("monitoring.coreos.com")
public class AlertmanagerList implements KubernetesListObject {
    @JsonProperty("apiVersion")
    private String apiVersion = "monitoring.coreos.com/v1";
    @JsonProperty("items")
    private List<io.fabric8.openshift.api.model.monitoring.v1.Alertmanager> items = new ArrayList();
    @JsonProperty("kind")
    private String kind = "AlertmanagerList";
    @JsonProperty("metadata")
    private ListMeta metadata;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public AlertmanagerList() {
    }

    public AlertmanagerList(String apiVersion, List<io.fabric8.openshift.api.model.monitoring.v1.Alertmanager> items, String kind, ListMeta metadata) {
        this.apiVersion = apiVersion;
        this.items = items;
        this.kind = kind;
        this.metadata = metadata;
    }

    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return this.apiVersion;
    }

    @JsonProperty("apiVersion")
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }



    @JsonProperty("items")
    public void setItems(List<Alertmanager> items) {
        this.items = items;
    }

    @JsonProperty("kind")
    public String getKind() {
        return this.kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

//    @JsonProperty("metadata")
//    public ListMeta getMetadata() {
//        return this.metadata;
//    }

    @JsonProperty("metadata")
    public void setMetadata(ListMeta metadata) {
        this.metadata = metadata;
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
        return "AlertmanagerList(apiVersion=" + this.getApiVersion() + ", items=" + this.getItems() + ", kind=" + this.getKind() + ", metadata=" + this.getMetadata() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }


    protected boolean canEqual(Object other) {
        return other instanceof io.fabric8.openshift.api.model.monitoring.v1.AlertmanagerList;
    }


    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public V1ListMeta getMetadata() {
        return null;
    }

    @Override
    public List<? extends KubernetesObject> getItems() {
        return null;
    }
}
