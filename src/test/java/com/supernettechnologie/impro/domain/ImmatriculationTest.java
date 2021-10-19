package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class ImmatriculationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Immatriculation.class);
        Immatriculation immatriculation1 = new Immatriculation();
        immatriculation1.setId(1L);
        Immatriculation immatriculation2 = new Immatriculation();
        immatriculation2.setId(immatriculation1.getId());
        assertThat(immatriculation1).isEqualTo(immatriculation2);
        immatriculation2.setId(2L);
        assertThat(immatriculation1).isNotEqualTo(immatriculation2);
        immatriculation1.setId(null);
        assertThat(immatriculation1).isNotEqualTo(immatriculation2);
    }
}
