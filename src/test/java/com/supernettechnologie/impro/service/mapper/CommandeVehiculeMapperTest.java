package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommandeVehiculeMapperTest {

    private CommandeVehiculeMapper commandeVehiculeMapper;

    @BeforeEach
    public void setUp() {
        commandeVehiculeMapper = new CommandeVehiculeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commandeVehiculeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commandeVehiculeMapper.fromId(null)).isNull();
    }
}
