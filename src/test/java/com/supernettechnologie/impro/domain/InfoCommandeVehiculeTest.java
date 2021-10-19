package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class InfoCommandeVehiculeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoCommandeVehicule.class);
        InfoCommandeVehicule infoCommandeVehicule1 = new InfoCommandeVehicule();
        infoCommandeVehicule1.setId(1L);
        InfoCommandeVehicule infoCommandeVehicule2 = new InfoCommandeVehicule();
        infoCommandeVehicule2.setId(infoCommandeVehicule1.getId());
        assertThat(infoCommandeVehicule1).isEqualTo(infoCommandeVehicule2);
        infoCommandeVehicule2.setId(2L);
        assertThat(infoCommandeVehicule1).isNotEqualTo(infoCommandeVehicule2);
        infoCommandeVehicule1.setId(null);
        assertThat(infoCommandeVehicule1).isNotEqualTo(infoCommandeVehicule2);
    }
}
