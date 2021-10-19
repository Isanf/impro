package com.supernettechnologie.impro.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.UserOtp} entity.
 */
public class UserOtpDTO implements Serializable {
    
    private Long id;

    private Long otpNumber;

    private Boolean otpUsed;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOtpNumber() {
        return otpNumber;
    }

    public void setOtpNumber(Long otpNumber) {
        this.otpNumber = otpNumber;
    }

    public Boolean isOtpUsed() {
        return otpUsed;
    }

    public void setOtpUsed(Boolean otpUsed) {
        this.otpUsed = otpUsed;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserOtpDTO)) {
            return false;
        }

        return id != null && id.equals(((UserOtpDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserOtpDTO{" +
            "id=" + getId() +
            ", otpNumber=" + getOtpNumber() +
            ", otpUsed='" + isOtpUsed() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
