package com.supernettechnologie.impro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserOtp.
 */
@Entity
@Table(name = "user_otp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserOtp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "otp_number")
    private Long otpNumber;

    @Column(name = "otp_used")
    private Boolean otpUsed;

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

    public Long getOtpNumber() {
        return otpNumber;
    }

    public UserOtp otpNumber(Long otpNumber) {
        this.otpNumber = otpNumber;
        return this;
    }

    public void setOtpNumber(Long otpNumber) {
        this.otpNumber = otpNumber;
    }

    public Boolean isOtpUsed() {
        return otpUsed;
    }

    public UserOtp otpUsed(Boolean otpUsed) {
        this.otpUsed = otpUsed;
        return this;
    }

    public void setOtpUsed(Boolean otpUsed) {
        this.otpUsed = otpUsed;
    }

    public User getUser() {
        return user;
    }

    public UserOtp user(User user) {
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
        if (!(o instanceof UserOtp)) {
            return false;
        }
        return id != null && id.equals(((UserOtp) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserOtp{" +
            "id=" + getId() +
            ", otpNumber=" + getOtpNumber() +
            ", otpUsed='" + isOtpUsed() + "'" +
            "}";
    }
}
