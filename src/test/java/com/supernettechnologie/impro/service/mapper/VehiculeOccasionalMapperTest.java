package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VehiculeOccasionalMapperTest {

    private VehiculeOccasionalMapper vehiculeOccasionalMapper;

    @BeforeEach
    public void setUp() {
        vehiculeOccasionalMapper = new VehiculeOccasionalMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(vehiculeOccasionalMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(vehiculeOccasionalMapper.fromId(null)).isNull();
    }
}
