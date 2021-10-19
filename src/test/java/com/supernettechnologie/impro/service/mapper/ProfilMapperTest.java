package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class ProfilMapperTest {

    private ProfilMapper profilMapper;

    @BeforeEach
    public void setUp() {
        profilMapper = new ProfilMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(profilMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(profilMapper.fromId(null)).isNull();
    }
}
