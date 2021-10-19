package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CarnetASoucheTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarnetASouche.class);
        CarnetASouche carnetASouche1 = new CarnetASouche();
        carnetASouche1.setId(1L);
        CarnetASouche carnetASouche2 = new CarnetASouche();
        carnetASouche2.setId(carnetASouche1.getId());
        assertThat(carnetASouche1).isEqualTo(carnetASouche2);
        carnetASouche2.setId(2L);
        assertThat(carnetASouche1).isNotEqualTo(carnetASouche2);
        carnetASouche1.setId(null);
        assertThat(carnetASouche1).isNotEqualTo(carnetASouche2);
    }
}
