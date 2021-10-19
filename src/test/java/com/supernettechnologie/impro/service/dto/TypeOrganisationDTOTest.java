package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeOrganisationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeOrganisationDTO.class);
        TypeOrganisationDTO typeOrganisationDTO1 = new TypeOrganisationDTO();
        typeOrganisationDTO1.setId(1L);
        TypeOrganisationDTO typeOrganisationDTO2 = new TypeOrganisationDTO();
        assertThat(typeOrganisationDTO1).isNotEqualTo(typeOrganisationDTO2);
        typeOrganisationDTO2.setId(typeOrganisationDTO1.getId());
        assertThat(typeOrganisationDTO1).isEqualTo(typeOrganisationDTO2);
        typeOrganisationDTO2.setId(2L);
        assertThat(typeOrganisationDTO1).isNotEqualTo(typeOrganisationDTO2);
        typeOrganisationDTO1.setId(null);
        assertThat(typeOrganisationDTO1).isNotEqualTo(typeOrganisationDTO2);
    }
}
