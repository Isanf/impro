package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class VenteMapperTest {

    private VenteMapper venteMapper;

    @BeforeEach
    public void setUp() {
        venteMapper = new VenteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(venteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(venteMapper.fromId(null)).isNull();
    }
}
