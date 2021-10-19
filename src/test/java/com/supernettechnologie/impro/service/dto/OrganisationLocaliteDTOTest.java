package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class OrganisationLocaliteDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationLocaliteDTO.class);
        OrganisationLocaliteDTO organisationLocaliteDTO1 = new OrganisationLocaliteDTO();
        organisationLocaliteDTO1.setId(1L);
        OrganisationLocaliteDTO organisationLocaliteDTO2 = new OrganisationLocaliteDTO();
        assertThat(organisationLocaliteDTO1).isNotEqualTo(organisationLocaliteDTO2);
        organisationLocaliteDTO2.setId(organisationLocaliteDTO1.getId());
        assertThat(organisationLocaliteDTO1).isEqualTo(organisationLocaliteDTO2);
        organisationLocaliteDTO2.setId(2L);
        assertThat(organisationLocaliteDTO1).isNotEqualTo(organisationLocaliteDTO2);
        organisationLocaliteDTO1.setId(null);
        assertThat(organisationLocaliteDTO1).isNotEqualTo(organisationLocaliteDTO2);
    }
}
