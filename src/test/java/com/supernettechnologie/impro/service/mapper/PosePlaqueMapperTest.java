package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PosePlaqueMapperTest {

    private PosePlaqueMapper posePlaqueMapper;

    @BeforeEach
    public void setUp() {
        posePlaqueMapper = new PosePlaqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(posePlaqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(posePlaqueMapper.fromId(null)).isNull();
    }
}
