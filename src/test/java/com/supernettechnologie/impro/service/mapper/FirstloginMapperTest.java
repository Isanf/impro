package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FirstloginMapperTest {

    private FirstloginMapper firstloginMapper;

    @BeforeEach
    public void setUp() {
        firstloginMapper = new FirstloginMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(firstloginMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(firstloginMapper.fromId(null)).isNull();
    }
}
