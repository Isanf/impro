package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeActeurMapperTest {

    private TypeActeurMapper typeActeurMapper;

    @BeforeEach
    public void setUp() {
        typeActeurMapper = new TypeActeurMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeActeurMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeActeurMapper.fromId(null)).isNull();
    }
}
