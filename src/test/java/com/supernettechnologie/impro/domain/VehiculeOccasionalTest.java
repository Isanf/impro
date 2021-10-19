package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class VehiculeOccasionalTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeOccasional.class);
        VehiculeOccasional vehiculeOccasional1 = new VehiculeOccasional();
        vehiculeOccasional1.setId(1L);
        VehiculeOccasional vehiculeOccasional2 = new VehiculeOccasional();
        vehiculeOccasional2.setId(vehiculeOccasional1.getId());
        assertThat(vehiculeOccasional1).isEqualTo(vehiculeOccasional2);
        vehiculeOccasional2.setId(2L);
        assertThat(vehiculeOccasional1).isNotEqualTo(vehiculeOccasional2);
        vehiculeOccasional1.setId(null);
        assertThat(vehiculeOccasional1).isNotEqualTo(vehiculeOccasional2);
    }
}
