package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PersonnePhysiqueMapperTest {

    private PersonnePhysiqueMapper personnePhysiqueMapper;

    @BeforeEach
    public void setUp() {
        personnePhysiqueMapper = new PersonnePhysiqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(personnePhysiqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(personnePhysiqueMapper.fromId(null)).isNull();
    }
}
