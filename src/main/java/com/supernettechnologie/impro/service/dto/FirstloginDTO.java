package com.supernettechnologie.impro.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.supernettechnologie.impro.domain.Firstlogin} entity.
 */
public class FirstloginDTO implements Serializable {

    private Long id;

    private Boolean passe;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isPasse() {
        return passe;
    }

    public void setPasse(Boolean passe) {
        this.passe = passe;
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FirstloginDTO firstloginDTO = (FirstloginDTO) o;
        if (firstloginDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), firstloginDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FirstloginDTO{" +
            "id=" + getId() +
            ", passe='" + isPasse() + "'" +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
