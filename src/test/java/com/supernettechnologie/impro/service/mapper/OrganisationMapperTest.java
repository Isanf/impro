package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganisationMapperTest {

    private OrganisationMapper organisationMapper;

    @BeforeEach
    public void setUp() {
        organisationMapper = new OrganisationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(organisationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(organisationMapper.fromId(null)).isNull();
    }
}
