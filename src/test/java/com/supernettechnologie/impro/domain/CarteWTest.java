package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CarteWTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteW.class);
        CarteW carteW1 = new CarteW();
        carteW1.setId(1L);
        CarteW carteW2 = new CarteW();
        carteW2.setId(carteW1.getId());
        assertThat(carteW1).isEqualTo(carteW2);
        carteW2.setId(2L);
        assertThat(carteW1).isNotEqualTo(carteW2);
        carteW1.setId(null);
        assertThat(carteW1).isNotEqualTo(carteW2);
    }
}
