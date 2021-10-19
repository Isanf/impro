package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class VehiculeOccasionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeOccasion.class);
        VehiculeOccasion vehiculeOccasion1 = new VehiculeOccasion();
        vehiculeOccasion1.setId(1L);
        VehiculeOccasion vehiculeOccasion2 = new VehiculeOccasion();
        vehiculeOccasion2.setId(vehiculeOccasion1.getId());
        assertThat(vehiculeOccasion1).isEqualTo(vehiculeOccasion2);
        vehiculeOccasion2.setId(2L);
        assertThat(vehiculeOccasion1).isNotEqualTo(vehiculeOccasion2);
        vehiculeOccasion1.setId(null);
        assertThat(vehiculeOccasion1).isNotEqualTo(vehiculeOccasion2);
    }
}
