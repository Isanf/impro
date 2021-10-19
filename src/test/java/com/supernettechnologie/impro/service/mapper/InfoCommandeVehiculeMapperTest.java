package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class InfoCommandeVehiculeMapperTest {

    private InfoCommandeVehiculeMapper infoCommandeVehiculeMapper;

    @BeforeEach
    public void setUp() {
        infoCommandeVehiculeMapper = new InfoCommandeVehiculeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(infoCommandeVehiculeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(infoCommandeVehiculeMapper.fromId(null)).isNull();
    }
}
