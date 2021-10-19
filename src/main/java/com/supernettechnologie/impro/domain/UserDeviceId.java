package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserDeviceId.
 */
@Entity
@Table(name = "user_device_id")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserDeviceId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "adress_mac")
    private String adressMac;

    @Column(name = "device_id")
    private String deviceId;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdressMac() {
        return adressMac;
    }

    public UserDeviceId adressMac(String adressMac) {
        this.adressMac = adressMac;
        return this;
    }

    public void setAdressMac(String adressMac) {
        this.adressMac = adressMac;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public UserDeviceId deviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public User getUser() {
        return user;
    }

    public UserDeviceId user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDeviceId)) {
            return false;
        }
        return id != null && id.equals(((UserDeviceId) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDeviceId{" +
            "id=" + getId() +
            ", adressMac='" + getAdressMac() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            "}";
    }
}
