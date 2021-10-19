package com.supernettechnologie.impro.repository;

import com.supernettechnologie.impro.domain.ImageModel;
import com.supernettechnologie.impro.domain.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Organisation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {
    Optional<ImageModel> findByName(String name);
    Optional<ImageModel> findByOrgid(Long id);
    void deleteByOrgid(Long orgid);
}
