package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CategorieOrganisationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieOrganisationDTO.class);
        CategorieOrganisationDTO categorieOrganisationDTO1 = new CategorieOrganisationDTO();
        categorieOrganisationDTO1.setId(1L);
        CategorieOrganisationDTO categorieOrganisationDTO2 = new CategorieOrganisationDTO();
        assertThat(categorieOrganisationDTO1).isNotEqualTo(categorieOrganisationDTO2);
        categorieOrganisationDTO2.setId(categorieOrganisationDTO1.getId());
        assertThat(categorieOrganisationDTO1).isEqualTo(categorieOrganisationDTO2);
        categorieOrganisationDTO2.setId(2L);
        assertThat(categorieOrganisationDTO1).isNotEqualTo(categorieOrganisationDTO2);
        categorieOrganisationDTO1.setId(null);
        assertThat(categorieOrganisationDTO1).isNotEqualTo(categorieOrganisationDTO2);
    }
}
