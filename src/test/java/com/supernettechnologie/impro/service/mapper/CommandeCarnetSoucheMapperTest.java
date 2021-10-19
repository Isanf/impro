package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CommandeCarnetSoucheMapperTest {

    private CommandeCarnetSoucheMapper commandeCarnetSoucheMapper;

    @BeforeEach
    public void setUp() {
        commandeCarnetSoucheMapper = new CommandeCarnetSoucheMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(commandeCarnetSoucheMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(commandeCarnetSoucheMapper.fromId(null)).isNull();
    }
}
