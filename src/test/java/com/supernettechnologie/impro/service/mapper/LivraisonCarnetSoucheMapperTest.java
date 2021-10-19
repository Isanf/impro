package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LivraisonCarnetSoucheMapperTest {

    private LivraisonCarnetSoucheMapper livraisonCarnetSoucheMapper;

    @BeforeEach
    public void setUp() {
        livraisonCarnetSoucheMapper = new LivraisonCarnetSoucheMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(livraisonCarnetSoucheMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(livraisonCarnetSoucheMapper.fromId(null)).isNull();
    }
}
