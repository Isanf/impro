package com.supernettechnologie.impro.repository;
import com.supernettechnologie.impro.domain.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findAllByConcessionnaireId(Long id);
    Page<Stock> findAllByConcessionnaireId(Pageable pageable, Long id);
}
