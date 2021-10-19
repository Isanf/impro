package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaqueGarageMapperTest {

    private PlaqueGarageMapper plaqueGarageMapper;

    @BeforeEach
    public void setUp() {
        plaqueGarageMapper = new PlaqueGarageMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(plaqueGarageMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(plaqueGarageMapper.fromId(null)).isNull();
    }
}
