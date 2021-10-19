package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class InfoCommandeCarnetASoucheTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoCommandeCarnetASouche.class);
        InfoCommandeCarnetASouche infoCommandeCarnetASouche1 = new InfoCommandeCarnetASouche();
        infoCommandeCarnetASouche1.setId(1L);
        InfoCommandeCarnetASouche infoCommandeCarnetASouche2 = new InfoCommandeCarnetASouche();
        infoCommandeCarnetASouche2.setId(infoCommandeCarnetASouche1.getId());
        assertThat(infoCommandeCarnetASouche1).isEqualTo(infoCommandeCarnetASouche2);
        infoCommandeCarnetASouche2.setId(2L);
        assertThat(infoCommandeCarnetASouche1).isNotEqualTo(infoCommandeCarnetASouche2);
        infoCommandeCarnetASouche1.setId(null);
        assertThat(infoCommandeCarnetASouche1).isNotEqualTo(infoCommandeCarnetASouche2);
    }
}
