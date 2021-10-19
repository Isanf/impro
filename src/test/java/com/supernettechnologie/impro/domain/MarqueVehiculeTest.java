package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class MarqueVehiculeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarqueVehicule.class);
        MarqueVehicule marqueVehicule1 = new MarqueVehicule();
        marqueVehicule1.setId(1L);
        MarqueVehicule marqueVehicule2 = new MarqueVehicule();
        marqueVehicule2.setId(marqueVehicule1.getId());
        assertThat(marqueVehicule1).isEqualTo(marqueVehicule2);
        marqueVehicule2.setId(2L);
        assertThat(marqueVehicule1).isNotEqualTo(marqueVehicule2);
        marqueVehicule1.setId(null);
        assertThat(marqueVehicule1).isNotEqualTo(marqueVehicule2);
    }
}
