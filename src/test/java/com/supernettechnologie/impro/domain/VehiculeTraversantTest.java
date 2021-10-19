package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class VehiculeTraversantTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeTraversant.class);
        VehiculeTraversant vehiculeTraversant1 = new VehiculeTraversant();
        vehiculeTraversant1.setId(1L);
        VehiculeTraversant vehiculeTraversant2 = new VehiculeTraversant();
        vehiculeTraversant2.setId(vehiculeTraversant1.getId());
        assertThat(vehiculeTraversant1).isEqualTo(vehiculeTraversant2);
        vehiculeTraversant2.setId(2L);
        assertThat(vehiculeTraversant1).isNotEqualTo(vehiculeTraversant2);
        vehiculeTraversant1.setId(null);
        assertThat(vehiculeTraversant1).isNotEqualTo(vehiculeTraversant2);
    }
}
