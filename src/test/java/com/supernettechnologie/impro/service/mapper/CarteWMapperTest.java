package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CarteWMapperTest {

    private CarteWMapper carteWMapper;

    @BeforeEach
    public void setUp() {
        carteWMapper = new CarteWMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(carteWMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(carteWMapper.fromId(null)).isNull();
    }
}
