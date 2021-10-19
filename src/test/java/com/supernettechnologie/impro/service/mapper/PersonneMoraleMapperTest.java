package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class PersonneMoraleMapperTest {

    private PersonneMoraleMapper personneMoraleMapper;

    @BeforeEach
    public void setUp() {
        personneMoraleMapper = new PersonneMoraleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(personneMoraleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(personneMoraleMapper.fromId(null)).isNull();
    }
}
