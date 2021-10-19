package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VehiculeTraversantMapperTest {

    private VehiculeTraversantMapper vehiculeTraversantMapper;

    @BeforeEach
    public void setUp() {
        vehiculeTraversantMapper = new VehiculeTraversantMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(vehiculeTraversantMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vehiculeTraversantMapper.fromId(null)).isNull();
    }
}
