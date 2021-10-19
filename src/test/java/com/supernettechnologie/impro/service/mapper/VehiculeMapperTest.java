package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VehiculeMapperTest {

    private VehiculeMapper vehiculeMapper;

    @BeforeEach
    public void setUp() {
        vehiculeMapper = new VehiculeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(vehiculeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vehiculeMapper.fromId(null)).isNull();
    }
}
