package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MarqueVehiculeMapperTest {

    private MarqueVehiculeMapper marqueVehiculeMapper;

    @BeforeEach
    public void setUp() {
        marqueVehiculeMapper = new MarqueVehiculeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(marqueVehiculeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(marqueVehiculeMapper.fromId(null)).isNull();
    }
}
