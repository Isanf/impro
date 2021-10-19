package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeOrganisationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOrganisation.class);
        TypeOrganisation typeOrganisation1 = new TypeOrganisation();
        typeOrganisation1.setId(1L);
        TypeOrganisation typeOrganisation2 = new TypeOrganisation();
        typeOrganisation2.setId(typeOrganisation1.getId());
        assertThat(typeOrganisation1).isEqualTo(typeOrganisation2);
        typeOrganisation2.setId(2L);
        assertThat(typeOrganisation1).isNotEqualTo(typeOrganisation2);
        typeOrganisation1.setId(null);
        assertThat(typeOrganisation1).isNotEqualTo(typeOrganisation2);
    }
}
