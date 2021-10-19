package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LivraisonVehiculeMapperTest {

    private LivraisonVehiculeMapper livraisonVehiculeMapper;

    @BeforeEach
    public void setUp() {
        livraisonVehiculeMapper = new LivraisonVehiculeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(livraisonVehiculeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(livraisonVehiculeMapper.fromId(null)).isNull();
    }
}
