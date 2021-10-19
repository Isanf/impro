package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class LivraisonVehiculeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivraisonVehicule.class);
        LivraisonVehicule livraisonVehicule1 = new LivraisonVehicule();
        livraisonVehicule1.setId(1L);
        LivraisonVehicule livraisonVehicule2 = new LivraisonVehicule();
        livraisonVehicule2.setId(livraisonVehicule1.getId());
        assertThat(livraisonVehicule1).isEqualTo(livraisonVehicule2);
        livraisonVehicule2.setId(2L);
        assertThat(livraisonVehicule1).isNotEqualTo(livraisonVehicule2);
        livraisonVehicule1.setId(null);
        assertThat(livraisonVehicule1).isNotEqualTo(livraisonVehicule2);
    }
}
