package com.supernettechnologie.impro.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.LogActivity} entity.
 */
public class LogActivityDTO implements Serializable {
    
    private Long id;

    private String principal;

    private String url;

    private String action;

    private String ip;

    private ZonedDateTime dateAction;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ZonedDateTime getDateAction() {
        return dateAction;
    }

    public void setDateAction(ZonedDateTime dateAction) {
        this.dateAction = dateAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogActivityDTO)) {
            return false;
        }

        return id != null && id.equals(((LogActivityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogActivityDTO{" +
            "id=" + getId() +
            ", principal='" + getPrincipal() + "'" +
            ", url='" + getUrl() + "'" +
            ", action='" + getAction() + "'" +
            ", ip='" + getIp() + "'" +
            ", dateAction='" + getDateAction() + "'" +
            "}";
    }
}
