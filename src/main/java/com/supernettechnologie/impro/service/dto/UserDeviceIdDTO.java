package com.supernettechnologie.impro.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.UserDeviceId} entity.
 */
public class UserDeviceIdDTO implements Serializable {
    
    private Long id;

    private String adressMac;

    private String deviceId;


    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdressMac() {
        return adressMac;
    }

    public void setAdressMac(String adressMac) {
        this.adressMac = adressMac;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
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
        if (!(o instanceof UserDeviceIdDTO)) {
            return false;
        }

        return id != null && id.equals(((UserDeviceIdDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDeviceIdDTO{" +
            "id=" + getId() +
            ", adressMac='" + getAdressMac() + "'" +
            ", deviceId='" + getDeviceId() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
