package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeVehiculeMapperTest {

    private TypeVehiculeMapper typeVehiculeMapper;

    @BeforeEach
    public void setUp() {
        typeVehiculeMapper = new TypeVehiculeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeVehiculeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeVehiculeMapper.fromId(null)).isNull();
    }
}
