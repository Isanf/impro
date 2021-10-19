package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
    Authority findByName(String name);
    List<Authority> findAllByName(String name);
}
