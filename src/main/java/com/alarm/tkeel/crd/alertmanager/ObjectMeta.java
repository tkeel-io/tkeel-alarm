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
import io.fabric8.kubernetes.api.model.ManagedFieldsEntry;
import io.fabric8.kubernetes.api.model.OwnerReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
        using = None.class
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "annotations", "clusterName", "creationTimestamp", "deletionGracePeriodSeconds", "deletionTimestamp", "finalizers", "generateName", "generation", "labels", "managedFields", "name", "namespace", "ownerReferences", "resourceVersion", "selfLink", "uid"})
public class ObjectMeta{
    @JsonProperty("annotations")
    private Map<String, String> annotations;
    @JsonProperty("clusterName")
    private String clusterName;
    @JsonProperty("creationTimestamp")
    private String creationTimestamp;
    @JsonProperty("deletionGracePeriodSeconds")
    private Long deletionGracePeriodSeconds;
    @JsonProperty("deletionTimestamp")
    private String deletionTimestamp;
    @JsonProperty("finalizers")
    @JsonInclude(Include.NON_EMPTY)
    private List<String> finalizers = new ArrayList();
    @JsonProperty("generateName")
    private String generateName;
    @JsonProperty("generation")
    private Long generation;
    @JsonProperty("labels")
    private Map<String, String> labels;
    @JsonProperty("managedFields")
    @JsonInclude(Include.NON_EMPTY)
    private List<ManagedFieldsEntry> managedFields = new ArrayList();
    @JsonProperty("name")
    private String name;
    @JsonProperty("namespace")
    private String namespace;
    @JsonProperty("ownerReferences")
    @JsonInclude(Include.NON_EMPTY)
    private List<OwnerReference> ownerReferences = new ArrayList();
    @JsonProperty("resourceVersion")
    private String resourceVersion;
    @JsonProperty("selfLink")
    private String selfLink;
    @JsonProperty("uid")
    private String uid;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public ObjectMeta() {
    }

    public ObjectMeta(Map<String, String> annotations, String clusterName, String creationTimestamp, Long deletionGracePeriodSeconds, String deletionTimestamp, List<String> finalizers, String generateName, Long generation, Map<String, String> labels, List<ManagedFieldsEntry> managedFields, String name, String namespace, List<OwnerReference> ownerReferences, String resourceVersion, String selfLink, String uid) {
        this.annotations = annotations;
        this.clusterName = clusterName;
        this.creationTimestamp = creationTimestamp;
        this.deletionGracePeriodSeconds = deletionGracePeriodSeconds;
        this.deletionTimestamp = deletionTimestamp;
        this.finalizers = finalizers;
        this.generateName = generateName;
        this.generation = generation;
        this.labels = labels;
        this.managedFields = managedFields;
        this.name = name;
        this.namespace = namespace;
        this.ownerReferences = ownerReferences;
        this.resourceVersion = resourceVersion;
        this.selfLink = selfLink;
        this.uid = uid;
    }

    @JsonProperty("annotations")
    public Map<String, String> getAnnotations() {
        return this.annotations;
    }

    @JsonProperty("annotations")
    public void setAnnotations(Map<String, String> annotations) {
        this.annotations = annotations;
    }

    @JsonProperty("clusterName")
    public String getClusterName() {
        return this.clusterName;
    }

    @JsonProperty("clusterName")
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @JsonProperty("creationTimestamp")
    public String getCreationTimestamp() {
        return this.creationTimestamp;
    }

    @JsonProperty("creationTimestamp")
    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    @JsonProperty("deletionGracePeriodSeconds")
    public Long getDeletionGracePeriodSeconds() {
        return this.deletionGracePeriodSeconds;
    }

    @JsonProperty("deletionGracePeriodSeconds")
    public void setDeletionGracePeriodSeconds(Long deletionGracePeriodSeconds) {
        this.deletionGracePeriodSeconds = deletionGracePeriodSeconds;
    }

    @JsonProperty("deletionTimestamp")
    public String getDeletionTimestamp() {
        return this.deletionTimestamp;
    }

    @JsonProperty("deletionTimestamp")
    public void setDeletionTimestamp(String deletionTimestamp) {
        this.deletionTimestamp = deletionTimestamp;
    }

    @JsonProperty("finalizers")
    public List<String> getFinalizers() {
        return this.finalizers;
    }

    @JsonProperty("finalizers")
    public void setFinalizers(List<String> finalizers) {
        this.finalizers = finalizers;
    }

    @JsonProperty("generateName")
    public String getGenerateName() {
        return this.generateName;
    }

    @JsonProperty("generateName")
    public void setGenerateName(String generateName) {
        this.generateName = generateName;
    }

    @JsonProperty("generation")
    public Long getGeneration() {
        return this.generation;
    }

    @JsonProperty("generation")
    public void setGeneration(Long generation) {
        this.generation = generation;
    }

    @JsonProperty("labels")
    public Map<String, String> getLabels() {
        return this.labels;
    }

    @JsonProperty("labels")
    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    @JsonProperty("managedFields")
    public List<ManagedFieldsEntry> getManagedFields() {
        return this.managedFields;
    }

    @JsonProperty("managedFields")
    public void setManagedFields(List<ManagedFieldsEntry> managedFields) {
        this.managedFields = managedFields;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("namespace")
    public String getNamespace() {
        return this.namespace;
    }

    @JsonProperty("namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @JsonProperty("ownerReferences")
    public List<OwnerReference> getOwnerReferences() {
        return this.ownerReferences;
    }

    @JsonProperty("ownerReferences")
    public void setOwnerReferences(List<OwnerReference> ownerReferences) {
        this.ownerReferences = ownerReferences;
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

    @JsonProperty("uid")
    public String getUid() {
        return this.uid;
    }

    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
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
        return "ObjectMeta(annotations=" + this.getAnnotations() + ", clusterName=" + this.getClusterName() + ", creationTimestamp=" + this.getCreationTimestamp() + ", deletionGracePeriodSeconds=" + this.getDeletionGracePeriodSeconds() + ", deletionTimestamp=" + this.getDeletionTimestamp() + ", finalizers=" + this.getFinalizers() + ", generateName=" + this.getGenerateName() + ", generation=" + this.getGeneration() + ", labels=" + this.getLabels() + ", managedFields=" + this.getManagedFields() + ", name=" + this.getName() + ", namespace=" + this.getNamespace() + ", ownerReferences=" + this.getOwnerReferences() + ", resourceVersion=" + this.getResourceVersion() + ", selfLink=" + this.getSelfLink() + ", uid=" + this.getUid() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }
//
//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof io.fabric8.kubernetes.api.model.ObjectMeta)) {
//            return false;
//        } else {
//            io.fabric8.kubernetes.api.model.ObjectMeta other = (io.fabric8.kubernetes.api.model.ObjectMeta)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                label215: {
//                    Object this$deletionGracePeriodSeconds = this.getDeletionGracePeriodSeconds();
//                    Object other$deletionGracePeriodSeconds = other.getDeletionGracePeriodSeconds();
//                    if (this$deletionGracePeriodSeconds == null) {
//                        if (other$deletionGracePeriodSeconds == null) {
//                            break label215;
//                        }
//                    } else if (this$deletionGracePeriodSeconds.equals(other$deletionGracePeriodSeconds)) {
//                        break label215;
//                    }
//
//                    return false;
//                }
//
//                Object this$generation = this.getGeneration();
//                Object other$generation = other.getGeneration();
//                if (this$generation == null) {
//                    if (other$generation != null) {
//                        return false;
//                    }
//                } else if (!this$generation.equals(other$generation)) {
//                    return false;
//                }
//
//                label201: {
//                    Object this$annotations = this.getAnnotations();
//                    Object other$annotations = other.getAnnotations();
//                    if (this$annotations == null) {
//                        if (other$annotations == null) {
//                            break label201;
//                        }
//                    } else if (this$annotations.equals(other$annotations)) {
//                        break label201;
//                    }
//
//                    return false;
//                }
//
//                Object this$clusterName = this.getClusterName();
//                Object other$clusterName = other.getClusterName();
//                if (this$clusterName == null) {
//                    if (other$clusterName != null) {
//                        return false;
//                    }
//                } else if (!this$clusterName.equals(other$clusterName)) {
//                    return false;
//                }
//
//                label187: {
//                    Object this$creationTimestamp = this.getCreationTimestamp();
//                    Object other$creationTimestamp = other.getCreationTimestamp();
//                    if (this$creationTimestamp == null) {
//                        if (other$creationTimestamp == null) {
//                            break label187;
//                        }
//                    } else if (this$creationTimestamp.equals(other$creationTimestamp)) {
//                        break label187;
//                    }
//
//                    return false;
//                }
//
//                Object this$deletionTimestamp = this.getDeletionTimestamp();
//                Object other$deletionTimestamp = other.getDeletionTimestamp();
//                if (this$deletionTimestamp == null) {
//                    if (other$deletionTimestamp != null) {
//                        return false;
//                    }
//                } else if (!this$deletionTimestamp.equals(other$deletionTimestamp)) {
//                    return false;
//                }
//
//                label173: {
//                    Object this$finalizers = this.getFinalizers();
//                    Object other$finalizers = other.getFinalizers();
//                    if (this$finalizers == null) {
//                        if (other$finalizers == null) {
//                            break label173;
//                        }
//                    } else if (this$finalizers.equals(other$finalizers)) {
//                        break label173;
//                    }
//
//                    return false;
//                }
//
//                label166: {
//                    Object this$generateName = this.getGenerateName();
//                    Object other$generateName = other.getGenerateName();
//                    if (this$generateName == null) {
//                        if (other$generateName == null) {
//                            break label166;
//                        }
//                    } else if (this$generateName.equals(other$generateName)) {
//                        break label166;
//                    }
//
//                    return false;
//                }
//
//                Object this$labels = this.getLabels();
//                Object other$labels = other.getLabels();
//                if (this$labels == null) {
//                    if (other$labels != null) {
//                        return false;
//                    }
//                } else if (!this$labels.equals(other$labels)) {
//                    return false;
//                }
//
//                label152: {
//                    Object this$managedFields = this.getManagedFields();
//                    Object other$managedFields = other.getManagedFields();
//                    if (this$managedFields == null) {
//                        if (other$managedFields == null) {
//                            break label152;
//                        }
//                    } else if (this$managedFields.equals(other$managedFields)) {
//                        break label152;
//                    }
//
//                    return false;
//                }
//
//                label145: {
//                    Object this$name = this.getName();
//                    Object other$name = other.getName();
//                    if (this$name == null) {
//                        if (other$name == null) {
//                            break label145;
//                        }
//                    } else if (this$name.equals(other$name)) {
//                        break label145;
//                    }
//
//                    return false;
//                }
//
//                Object this$namespace = this.getNamespace();
//                Object other$namespace = other.getNamespace();
//                if (this$namespace == null) {
//                    if (other$namespace != null) {
//                        return false;
//                    }
//                } else if (!this$namespace.equals(other$namespace)) {
//                    return false;
//                }
//
//                Object this$ownerReferences = this.getOwnerReferences();
//                Object other$ownerReferences = other.getOwnerReferences();
//                if (this$ownerReferences == null) {
//                    if (other$ownerReferences != null) {
//                        return false;
//                    }
//                } else if (!this$ownerReferences.equals(other$ownerReferences)) {
//                    return false;
//                }
//
//                label124: {
//                    Object this$resourceVersion = this.getResourceVersion();
//                    Object other$resourceVersion = other.getResourceVersion();
//                    if (this$resourceVersion == null) {
//                        if (other$resourceVersion == null) {
//                            break label124;
//                        }
//                    } else if (this$resourceVersion.equals(other$resourceVersion)) {
//                        break label124;
//                    }
//
//                    return false;
//                }
//
//                Object this$selfLink = this.getSelfLink();
//                Object other$selfLink = other.getSelfLink();
//                if (this$selfLink == null) {
//                    if (other$selfLink != null) {
//                        return false;
//                    }
//                } else if (!this$selfLink.equals(other$selfLink)) {
//                    return false;
//                }
//
//                Object this$uid = this.getUid();
//                Object other$uid = other.getUid();
//                if (this$uid == null) {
//                    if (other$uid != null) {
//                        return false;
//                    }
//                } else if (!this$uid.equals(other$uid)) {
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
        return other instanceof io.fabric8.kubernetes.api.model.ObjectMeta;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $deletionGracePeriodSeconds = this.getDeletionGracePeriodSeconds();
//        int result = result * 59 + ($deletionGracePeriodSeconds == null ? 43 : $deletionGracePeriodSeconds.hashCode());
//        Object $generation = this.getGeneration();
//        result = result * 59 + ($generation == null ? 43 : $generation.hashCode());
//        Object $annotations = this.getAnnotations();
//        result = result * 59 + ($annotations == null ? 43 : $annotations.hashCode());
//        Object $clusterName = this.getClusterName();
//        result = result * 59 + ($clusterName == null ? 43 : $clusterName.hashCode());
//        Object $creationTimestamp = this.getCreationTimestamp();
//        result = result * 59 + ($creationTimestamp == null ? 43 : $creationTimestamp.hashCode());
//        Object $deletionTimestamp = this.getDeletionTimestamp();
//        result = result * 59 + ($deletionTimestamp == null ? 43 : $deletionTimestamp.hashCode());
//        Object $finalizers = this.getFinalizers();
//        result = result * 59 + ($finalizers == null ? 43 : $finalizers.hashCode());
//        Object $generateName = this.getGenerateName();
//        result = result * 59 + ($generateName == null ? 43 : $generateName.hashCode());
//        Object $labels = this.getLabels();
//        result = result * 59 + ($labels == null ? 43 : $labels.hashCode());
//        Object $managedFields = this.getManagedFields();
//        result = result * 59 + ($managedFields == null ? 43 : $managedFields.hashCode());
//        Object $name = this.getName();
//        result = result * 59 + ($name == null ? 43 : $name.hashCode());
//        Object $namespace = this.getNamespace();
//        result = result * 59 + ($namespace == null ? 43 : $namespace.hashCode());
//        Object $ownerReferences = this.getOwnerReferences();
//        result = result * 59 + ($ownerReferences == null ? 43 : $ownerReferences.hashCode());
//        Object $resourceVersion = this.getResourceVersion();
//        result = result * 59 + ($resourceVersion == null ? 43 : $resourceVersion.hashCode());
//        Object $selfLink = this.getSelfLink();
//        result = result * 59 + ($selfLink == null ? 43 : $selfLink.hashCode());
//        Object $uid = this.getUid();
//        result = result * 59 + ($uid == null ? 43 : $uid.hashCode());
//        Object $additionalProperties = this.getAdditionalProperties();
//        result = result * 59 + ($additionalProperties == null ? 43 : $additionalProperties.hashCode());
//        return result;
//    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

//    @Override
//    public ObjectMeta getMetadata() {
//        return null;
//    }
//
//    @Override
//    public String getApiVersion() {
//        return null;
//    }
//
//    @Override
//    public String getKind() {
//        return null;
//    }
}
