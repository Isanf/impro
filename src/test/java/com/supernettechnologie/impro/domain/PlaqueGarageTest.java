package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PlaqueGarageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaqueGarage.class);
        PlaqueGarage plaqueGarage1 = new PlaqueGarage();
        plaqueGarage1.setId(1L);
        PlaqueGarage plaqueGarage2 = new PlaqueGarage();
        plaqueGarage2.setId(plaqueGarage1.getId());
        assertThat(plaqueGarage1).isEqualTo(plaqueGarage2);
        plaqueGarage2.setId(2L);
        assertThat(plaqueGarage1).isNotEqualTo(plaqueGarage2);
        plaqueGarage1.setId(null);
        assertThat(plaqueGarage1).isNotEqualTo(plaqueGarage2);
    }
}
