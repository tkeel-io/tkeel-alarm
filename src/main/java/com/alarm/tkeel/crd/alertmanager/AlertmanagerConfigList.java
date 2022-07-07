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
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;
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
@Version("v1alpha1")
@Group("monitoring.coreos.com")
public class AlertmanagerConfigList implements KubernetesListObject {
    @JsonProperty("apiVersion")
    private String apiVersion = "monitoring.coreos.com/v1alpha1";
    @JsonProperty("items")
    private List<io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfig> items = new ArrayList();
    @JsonProperty("kind")
    private String kind = "AlertmanagerConfigList";
    @JsonProperty("metadata")
    private ListMeta metadata;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public AlertmanagerConfigList() {
    }

    public AlertmanagerConfigList(String apiVersion, List<io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfig> items, String kind, ListMeta metadata) {
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

//    @JsonProperty("items")
//    public List<io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfig> getItems() {
//        return this.items;
//    }

//    @JsonProperty("items")
//    public void setItems(List<AlertmanagerConfig> items) {
//        this.items = items;
//    }

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

//    public String toString() {
//        return "AlertmanagerConfigList(apiVersion=" + this.getApiVersion() + ", items=" + this.getItems() + ", kind=" + this.getKind() + ", metadata=" + this.getMetadata() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
//    }

//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigList)) {
//            return false;
//        } else {
//            io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigList other = (io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigList)o;
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
//                Object this$items = this.getItems();
//                Object other$items = other.getItems();
//                if (this$items == null) {
//                    if (other$items != null) {
//                        return false;
//                    }
//                } else if (!this$items.equals(other$items)) {
//                    return false;
//                }
//
//                label57: {
//                    Object this$kind = this.getKind();
//                    Object other$kind = other.getKind();
//                    if (this$kind == null) {
//                        if (other$kind == null) {
//                            break label57;
//                        }
//                    } else if (this$kind.equals(other$kind)) {
//                        break label57;
//                    }
//
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
        return other instanceof io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigList;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $apiVersion = this.getApiVersion();
//        int result = result * 59 + ($apiVersion == null ? 43 : $apiVersion.hashCode());
//        Object $items = this.getItems();
//        result = result * 59 + ($items == null ? 43 : $items.hashCode());
//        Object $kind = this.getKind();
//        result = result * 59 + ($kind == null ? 43 : $kind.hashCode());
//        Object $metadata = this.getMetadata();
//        result = result * 59 + ($metadata == null ? 43 : $metadata.hashCode());
//        Object $additionalProperties = this.getAdditionalProperties();
//        result = result * 59 + ($additionalProperties == null ? 43 : $additionalProperties.hashCode());
//        return result;
//    }

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
