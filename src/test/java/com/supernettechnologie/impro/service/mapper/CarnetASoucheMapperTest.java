package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarnetASoucheMapperTest {

    private CarnetASoucheMapper carnetASoucheMapper;

    @BeforeEach
    public void setUp() {
        carnetASoucheMapper = new CarnetASoucheMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(carnetASoucheMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(carnetASoucheMapper.fromId(null)).isNull();
    }
}
