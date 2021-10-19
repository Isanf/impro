package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class StatistiqueMapperTest {

    private StatistiqueMapper statistiqueMapper;

    @BeforeEach
    public void setUp() {
        statistiqueMapper = new StatistiqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(statistiqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(statistiqueMapper.fromId(null)).isNull();
    }
}
