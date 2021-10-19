package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ImmatriculationMapperTest {

    private ImmatriculationMapper immatriculationMapper;

    @BeforeEach
    public void setUp() {
        immatriculationMapper = new ImmatriculationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(immatriculationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(immatriculationMapper.fromId(null)).isNull();
    }
}
