package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeCarnetMapperTest {

    private TypeCarnetMapper typeCarnetMapper;

    @BeforeEach
    public void setUp() {
        typeCarnetMapper = new TypeCarnetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeCarnetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeCarnetMapper.fromId(null)).isNull();
    }
}
