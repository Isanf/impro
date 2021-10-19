package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.PosePlaque;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PosePlaque entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PosePlaqueRepository extends JpaRepository<PosePlaque, Long> {

}
