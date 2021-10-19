package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PlaqueImmatriculationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaqueImmatriculation.class);
        PlaqueImmatriculation plaqueImmatriculation1 = new PlaqueImmatriculation();
        plaqueImmatriculation1.setId(1L);
        PlaqueImmatriculation plaqueImmatriculation2 = new PlaqueImmatriculation();
        plaqueImmatriculation2.setId(plaqueImmatriculation1.getId());
        assertThat(plaqueImmatriculation1).isEqualTo(plaqueImmatriculation2);
        plaqueImmatriculation2.setId(2L);
        assertThat(plaqueImmatriculation1).isNotEqualTo(plaqueImmatriculation2);
        plaqueImmatriculation1.setId(null);
        assertThat(plaqueImmatriculation1).isNotEqualTo(plaqueImmatriculation2);
    }
}
