package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class OrganisationLocaliteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganisationLocalite.class);
        OrganisationLocalite organisationLocalite1 = new OrganisationLocalite();
        organisationLocalite1.setId(1L);
        OrganisationLocalite organisationLocalite2 = new OrganisationLocalite();
        organisationLocalite2.setId(organisationLocalite1.getId());
        assertThat(organisationLocalite1).isEqualTo(organisationLocalite2);
        organisationLocalite2.setId(2L);
        assertThat(organisationLocalite1).isNotEqualTo(organisationLocalite2);
        organisationLocalite1.setId(null);
        assertThat(organisationLocalite1).isNotEqualTo(organisationLocalite2);
    }
}
