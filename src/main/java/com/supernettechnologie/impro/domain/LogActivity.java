package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A LogActivity.
 */
@Entity
@Table(name = "log_activity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LogActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "principal")
    private String principal;

    @Column(name = "url")
    private String url;

    @Column(name = "action")
    private String action;

    @Column(name = "ip")
    private String ip;

    @Column(name = "date_action")
    private ZonedDateTime dateAction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrincipal() {
        return principal;
    }

    public LogActivity principal(String principal) {
        this.principal = principal;
        return this;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String getUrl() {
        return url;
    }

    public LogActivity url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAction() {
        return action;
    }

    public LogActivity action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIp() {
        return ip;
    }

    public LogActivity ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ZonedDateTime getDateAction() {
        return dateAction;
    }

    public LogActivity dateAction(ZonedDateTime dateAction) {
        this.dateAction = dateAction;
        return this;
    }

    public void setDateAction(ZonedDateTime dateAction) {
        this.dateAction = dateAction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogActivity)) {
            return false;
        }
        return id != null && id.equals(((LogActivity) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogActivity{" +
            "id=" + getId() +
            ", principal='" + getPrincipal() + "'" +
            ", url='" + getUrl() + "'" +
            ", action='" + getAction() + "'" +
            ", ip='" + getIp() + "'" +
            ", dateAction='" + getDateAction() + "'" +
            "}";
    }
}
