package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.LogActivity;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LogActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogActivityRepository extends JpaRepository<LogActivity, Long> {
}
