package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.User;
import com.supernettechnologie.impro.domain.UserOtp;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the UserOtp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findByOtpNumber(Long otp);
    Optional<UserOtp> findByUser(User user);
}
