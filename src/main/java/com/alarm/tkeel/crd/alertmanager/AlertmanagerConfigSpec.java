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
import io.fabric8.openshift.api.model.monitoring.v1alpha1.InhibitRule;
import io.fabric8.openshift.api.model.monitoring.v1alpha1.Receiver;
//import io.fabric8.openshift.api.model.monitoring.v1alpha1.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonDeserialize(
        using = None.class
)
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({"apiVersion", "kind", "metadata", "inhibitRules", "receivers", "route"})
public class AlertmanagerConfigSpec implements KubernetesResource {
    @JsonProperty("inhibitRules")
    @JsonInclude(Include.NON_EMPTY)
    private List<InhibitRule> inhibitRules = new ArrayList();
    @JsonProperty("receivers")
    private List<Receiver> receivers = new ArrayList();
    @JsonProperty("route")
    private Route route;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap();

    public AlertmanagerConfigSpec() {
    }

    public AlertmanagerConfigSpec(List<InhibitRule> inhibitRules, List<Receiver> receivers, Route route) {
        this.inhibitRules = inhibitRules;
        this.receivers = receivers;
        this.route = route;
    }

    @JsonProperty("inhibitRules")
    public List<InhibitRule> getInhibitRules() {
        return this.inhibitRules;
    }

    @JsonProperty("inhibitRules")
    public void setInhibitRules(List<InhibitRule> inhibitRules) {
        this.inhibitRules = inhibitRules;
    }

    @JsonProperty("receivers")
    public List<Receiver> getReceivers() {
        return this.receivers;
    }

    @JsonProperty("receivers")
    public void setReceivers(List<Receiver> receivers) {
        this.receivers = receivers;
    }

    @JsonProperty("route")
    public Route getRoute() {
        return this.route;
    }

    @JsonProperty("route")
    public void setRoute(Route route) {
        this.route = route;
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
        return "AlertmanagerConfigSpec(inhibitRules=" + this.getInhibitRules() + ", receivers=" + this.getReceivers() + ", route=" + this.getRoute() + ", additionalProperties=" + this.getAdditionalProperties() + ")";
    }

//    public boolean equals(Object o) {
//        if (o == this) {
//            return true;
//        } else if (!(o instanceof io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigSpec)) {
//            return false;
//        } else {
//            io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigSpec other = (io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigSpec)o;
//            if (!other.canEqual(this)) {
//                return false;
//            } else {
//                label59: {
//                    Object this$inhibitRules = this.getInhibitRules();
//                    Object other$inhibitRules = other.getInhibitRules();
//                    if (this$inhibitRules == null) {
//                        if (other$inhibitRules == null) {
//                            break label59;
//                        }
//                    } else if (this$inhibitRules.equals(other$inhibitRules)) {
//                        break label59;
//                    }
//
//                    return false;
//                }
//
//                Object this$receivers = this.getReceivers();
//                Object other$receivers = other.getReceivers();
//                if (this$receivers == null) {
//                    if (other$receivers != null) {
//                        return false;
//                    }
//                } else if (!this$receivers.equals(other$receivers)) {
//                    return false;
//                }
//
//                Object this$route = this.getRoute();
//                Object other$route = other.getRoute();
//                if (this$route == null) {
//                    if (other$route != null) {
//                        return false;
//                    }
//                } else if (!this$route.equals(other$route)) {
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
        return other instanceof io.fabric8.openshift.api.model.monitoring.v1alpha1.AlertmanagerConfigSpec;
    }

//    public int hashCode() {
//        int PRIME = true;
//        int result = 1;
//        Object $inhibitRules = this.getInhibitRules();
//        int result = result * 59 + ($inhibitRules == null ? 43 : $inhibitRules.hashCode());
//        Object $receivers = this.getReceivers();
//        result = result * 59 + ($receivers == null ? 43 : $receivers.hashCode());
//        Object $route = this.getRoute();
//        result = result * 59 + ($route == null ? 43 : $route.hashCode());
//        Object $additionalProperties = this.getAdditionalProperties();
//        result = result * 59 + ($additionalProperties == null ? 43 : $additionalProperties.hashCode());
//        return result;
//    }

    @JsonIgnore
    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}