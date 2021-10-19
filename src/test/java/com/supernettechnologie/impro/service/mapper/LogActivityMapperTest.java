package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LogActivityMapperTest {

    private LogActivityMapper logActivityMapper;

    @BeforeEach
    public void setUp() {
        logActivityMapper = new LogActivityMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(logActivityMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(logActivityMapper.fromId(null)).isNull();
    }
}
