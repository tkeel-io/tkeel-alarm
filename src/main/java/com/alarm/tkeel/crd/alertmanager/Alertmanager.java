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
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.Namespaced;
//import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
import io.fabric8.openshift.api.model.monitoring.v1.AlertmanagerSpec;
import io.fabric8.openshift.api.model.monitoring.v1.AlertmanagerStatus;
import io.kubernetes.client.common.KubernetesObject;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
//import io.kubernetes.client.openapi.models.V1ObjectMeta;

import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(
        using = None.class
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "spec", "status"})
@Version("v1")
@Group("monitoring.coreos.com")
public class Alertmanager implements KubernetesObject {
    @JsonProperty("apiVersion")
    private String apiVersion = "monitoring.coreos.com/v1";
    @JsonProperty("kind")
    private String kind = "Alertmanager";
    @JsonProperty("metadata")
    private V1ObjectMeta metadata;
    @JsonProperty("spec")
    private AlertmanagerSpec spec;
    @JsonProperty("status")
    private AlertmanagerStatus status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public Alertmanager() {
    }

    public Alertmanager(String apiVersion, String kind, V1ObjectMeta metadata, AlertmanagerSpec spec, AlertmanagerStatus status) {
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.spec = spec;
        this.status = status;
    }

    @JsonProperty("apiVersion")
    public String getApiVersion() {
        return this.apiVersion;
    }

    @JsonProperty("apiVersion")
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @JsonProperty("kind")
    public String getKind() {
        return this.kind;
    }

    @JsonProperty("kind")
    public void setKind(String kind) {
        this.kind = kind;
    }

    @JsonProperty("metadata")
    public void setMetadata(V1ObjectMeta metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("spec")
    public AlertmanagerSpec getSpec() {
        return this.spec;
    }

    @JsonProperty("spec")
    public void setSpec(AlertmanagerSpec spec) {
        this.spec = spec;
    }

    @JsonProperty("status")
    public AlertmanagerStatus getStatus() {
        return this.status;
    }

    @JsonProperty("status")
    public void setStatus(AlertmanagerStatus status) {
        this.status = status;
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
        return "Alertmanager(apiVersion=" + this.getApiVersion() + ", kind=" + this.getKind() + ", metadata=" + this.getMetadata() + ", spec=" + this.getSpec() + ", status=" + this.getStatus() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }

//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof io.fabric8.openshift.api.model.monitoring.v1.Alertmanager)) {
//            return false;
//        } else {
//            io.fabric8.openshift.api.model.monitoring.v1.Alertmanager other = (io.fabric8.openshift.api.model.monitoring.v1.Alertmanager)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                Object this$apiVersion = this.getApiVersion();
//                Object other$apiVersion = other.getApiVersion();
//                if (this$apiVersion == null) {
//                    if (other$apiVersion != null) {
//                        return false;
//                    }
//                } else if (!this$apiVersion.equals(other$apiVersion)) {
//                    return false;
//                }
//
//                Object this$kind = this.getKind();
//                Object other$kind = other.getKind();
//                if (this$kind == null) {
//                    if (other$kind != null) {
//                        return false;
//                    }
//                } else if (!this$kind.equals(other$kind)) {
//                    return false;
//                }
//
//                Object this$metadata = this.getMetadata();
//                Object other$metadata = other.getMetadata();
//                if (this$metadata == null) {
//                    if (other$metadata != null) {
//                        return false;
//                    }
//                } else if (!this$metadata.equals(other$metadata)) {
//                    return false;
//                }
//
//                label62: {
//                    Object this$spec = this.getSpec();
//                    Object other$spec = other.getSpec();
//                    if (this$spec == null) {
//                        if (other$spec == null) {
//                            break label62;
//                        }
//                    } else if (this$spec.equals(other$spec)) {
//                        break label62;
//                    }
//
//                    return false;
//                }
//
//                label55: {
//                    Object this$status = this.getStatus();
//                    Object other$status = other.getStatus();
//                    if (this$status == null) {
//                        if (other$status == null) {
//                            break label55;
//                        }
//                    } else if (this$status.equals(other$status)) {
//                        break label55;
//                    }
//
//                    return false;
//                }
//
//                Object this$additionalProperties = this.getAdditionalProperties();
//                Object other$additionalProperties = other.getAdditionalProperties();
//                if (this$additionalProperties == null) {
//                    if (other$additionalProperties != null) {
//                        return false;
//                    }
//                } else if (!this$additionalProperties.equals(other$additionalProperties)) {
//                    return false;
//                }
//
//                return true;
//            }
//        }
//    }

    protected boolean canEqual(Object other) {
        return other instanceof io.fabric8.openshift.api.model.monitoring.v1.Alertmanager;
    }



    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    @Override
    public V1ObjectMeta getMetadata() {
        return this.metadata;
    }
}
