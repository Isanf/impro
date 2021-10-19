package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.UserDeviceId;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the UserDeviceId entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserDeviceIdRepository extends JpaRepository<UserDeviceId, Long> {
    Optional<UserDeviceId> findByDeviceId(String id);
}
