package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InfoCommandeCarnetASoucheMapperTest {

    private InfoCommandeCarnetASoucheMapper infoCommandeCarnetASoucheMapper;

    @BeforeEach
    public void setUp() {
        infoCommandeCarnetASoucheMapper = new InfoCommandeCarnetASoucheMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(infoCommandeCarnetASoucheMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(infoCommandeCarnetASoucheMapper.fromId(null)).isNull();
    }
}
