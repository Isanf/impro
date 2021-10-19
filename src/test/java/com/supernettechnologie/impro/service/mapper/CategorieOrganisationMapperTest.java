package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CategorieOrganisationMapperTest {

    private CategorieOrganisationMapper categorieOrganisationMapper;

    @BeforeEach
    public void setUp() {
        categorieOrganisationMapper = new CategorieOrganisationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(categorieOrganisationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(categorieOrganisationMapper.fromId(null)).isNull();
    }
}
