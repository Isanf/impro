package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaqueImmatriculationMapperTest {

    private PlaqueImmatriculationMapper plaqueImmatriculationMapper;

    @BeforeEach
    public void setUp() {
        plaqueImmatriculationMapper = new PlaqueImmatriculationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(plaqueImmatriculationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(plaqueImmatriculationMapper.fromId(null)).isNull();
    }
}
