package com.alarm.tkeel.crd.alertmanager;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * @Author guojun
 * @Description 开心工作，快乐生活
 * @Date 2022/06/15/16:44
 */
public class AMGlobal {

    public AMGlobal() {

    }

    // TODO this is a hard-coded default. Different way?
    private String resolveTimeout = "1m";

    private String smtpSmarthost;
    private String smtpFrom;
    private boolean smtpRequireTls;

    @JsonProperty("resolve_timeout")
    public String getResolveTimeout() {
        return resolveTimeout;
    }

    @JsonProperty("resolve_timeout")
    private void setResolveTimeout(String resolveTimeout) {
        this.resolveTimeout = resolveTimeout;
    }

    private AMGlobal resolveTimeout(String resolveTimeout) {
        this.resolveTimeout = resolveTimeout;
        return this;
    }

    @JsonProperty("smtp_smarthost")
    public String getSmtpSmarthost() {
        return smtpSmarthost;
    }

    @JsonProperty("smtp_smarthost")
    public void setSmtpSmarthost(String smtpSmarthost) {
        this.smtpSmarthost = smtpSmarthost;
    }

    @JsonProperty("smtp_from")
    public String getSmtpFrom() {
        return smtpFrom;
    }

    @JsonProperty("smtp_from")
    public void setSmtpFrom(String smtpFrom) {
        this.smtpFrom = smtpFrom;
    }

    @JsonProperty("smtp_require_tls")
    public boolean isSmtpRequireTls() {
        return smtpRequireTls;
    }

    @JsonProperty("smtp_require_tls")
    public void setSmtpRequireTls(boolean smtpRequireTls) {
        this.smtpRequireTls = smtpRequireTls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AMGlobal)) return false;
        AMGlobal amGlobal = (AMGlobal) o;
        return Objects.equals(getResolveTimeout(), amGlobal.getResolveTimeout());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResolveTimeout());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AMGlobal.class.getSimpleName() + "[", "]")
                .add("resolveTimeout='" + resolveTimeout + "'")
                .toString();
    }
}