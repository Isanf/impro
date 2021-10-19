package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CategorieOrganisationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorieOrganisation.class);
        CategorieOrganisation categorieOrganisation1 = new CategorieOrganisation();
        categorieOrganisation1.setId(1L);
        CategorieOrganisation categorieOrganisation2 = new CategorieOrganisation();
        categorieOrganisation2.setId(categorieOrganisation1.getId());
        assertThat(categorieOrganisation1).isEqualTo(categorieOrganisation2);
        categorieOrganisation2.setId(2L);
        assertThat(categorieOrganisation1).isNotEqualTo(categorieOrganisation2);
        categorieOrganisation1.setId(null);
        assertThat(categorieOrganisation1).isNotEqualTo(categorieOrganisation2);
    }
}
