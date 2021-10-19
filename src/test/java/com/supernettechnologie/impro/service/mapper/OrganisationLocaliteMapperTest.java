package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OrganisationLocaliteMapperTest {

    private OrganisationLocaliteMapper organisationLocaliteMapper;

    @BeforeEach
    public void setUp() {
        organisationLocaliteMapper = new OrganisationLocaliteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(organisationLocaliteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(organisationLocaliteMapper.fromId(null)).isNull();
    }
}
