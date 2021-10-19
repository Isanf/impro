package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeOrganisationMapperTest {

    private TypeOrganisationMapper typeOrganisationMapper;

    @BeforeEach
    public void setUp() {
        typeOrganisationMapper = new TypeOrganisationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeOrganisationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeOrganisationMapper.fromId(null)).isNull();
    }
}
