package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class VehiculeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicule.class);
        Vehicule vehicule1 = new Vehicule();
        vehicule1.setId(1L);
        Vehicule vehicule2 = new Vehicule();
        vehicule2.setId(vehicule1.getId());
        assertThat(vehicule1).isEqualTo(vehicule2);
        vehicule2.setId(2L);
        assertThat(vehicule1).isNotEqualTo(vehicule2);
        vehicule1.setId(null);
        assertThat(vehicule1).isNotEqualTo(vehicule2);
    }
}
