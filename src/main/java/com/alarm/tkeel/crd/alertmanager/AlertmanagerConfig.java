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
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
//import io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigSpec;
import io.kubernetes.client.openapi.models.V1ObjectMeta;

import java.util.HashMap;
import java.util.Map;

@JsonDeserialize(
        using = None.class
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "spec"})
@Version("v1alpha1")
@Group("monitoring.coreos.com")
public class AlertmanagerConfig implements io.kubernetes.client.common.KubernetesObject {
    @JsonProperty("apiVersion")
    private String apiVersion = "monitoring.coreos.com/v1alpha1";
    @JsonProperty("kind")
    private String kind = "AlertmanagerConfig";
    @JsonProperty("metadata")
    private V1ObjectMeta metadata;
    @JsonProperty("spec")
    private AlertmanagerConfigSpec spec;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public AlertmanagerConfig() {
    }

    public AlertmanagerConfig(String apiVersion, String kind, V1ObjectMeta metadata, AlertmanagerConfigSpec spec) {
        this.apiVersion = apiVersion;
        this.kind = kind;
        this.metadata = metadata;
        this.spec = spec;
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
    public V1ObjectMeta getMetadata() {
        return this.metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(V1ObjectMeta metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("spec")
    public AlertmanagerConfigSpec getSpec() {
        return this.spec;
    }

    @JsonProperty("spec")
    public void setSpec(AlertmanagerConfigSpec spec) {
        this.spec = spec;
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
        return "AlertmanagerConfig(apiVersion=" + this.getApiVersion() + ", kind=" + this.getKind() + ", metadata=" + this.getMetadata() + ", spec=" + this.getSpec() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }

//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfig)) {
//            return false;
//        } else {
//            io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfig other = (io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfig)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                label71: {
//                    Object this$apiVersion = this.getApiVersion();
//                    Object other$apiVersion = other.getApiVersion();
//                    if (this$apiVersion == null) {
//                        if (other$apiVersion == null) {
//                            break label71;
//                        }
//                    } else if (this$apiVersion.equals(other$apiVersion)) {
//                        break label71;
//                    }
//
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
//                label57: {
//                    Object this$metadata = this.getMetadata();
//                    Object other$metadata = other.getMetadata();
//                    if (this$metadata == null) {
//                        if (other$metadata == null) {
//                            break label57;
//                        }
//                    } else if (this$metadata.equals(other$metadata)) {
//                        break label57;
//                    }
//
//                    return false;
//                }
//
//                Object this$spec = this.getSpec();
//                Object other$spec = other.getSpec();
//                if (this$spec == null) {
//                    if (other$spec != null) {
//                        return false;
//                    }
//                } else if (!this$spec.equals(other$spec)) {
//                    return false;
//                }
//
//                Object this$additionalProperties = this.getAdditionalProperties();
//                Object other$additionalProperties = other.getAdditionalProperties();
//                if (this$additionalProperties == null) {
//                    if (other$additionalProperties == null) {
//                        return true;
//                    }
//                } else if (this$additionalProperties.equals(other$additionalProperties)) {
//                    return true;
//                }
//
//                return false;
//            }
//        }
//    }

    protected boolean canEqual(Object other) {
        return other instanceof io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfig;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $apiVersion = this.getApiVersion();
//        int result = result * 59 + ($apiVersion == null ? 43 : $apiVersion.hashCode());
//        Object $kind = this.getKind();
//        result = result * 59 + ($kind == null ? 43 : $kind.hashCode());
//        Object $metadata = this.getMetadata();
//        result = result * 59 + ($metadata == null ? 43 : $metadata.hashCode());
//        Object $spec = this.getSpec();
//        result = result * 59 + ($spec == null ? 43 : $spec.hashCode());
//        Object $additionalProperties = this.getAdditionalProperties();
//        result = result * 59 + ($additionalProperties == null ? 43 : $additionalProperties.hashCode());
//        return result;
//    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}