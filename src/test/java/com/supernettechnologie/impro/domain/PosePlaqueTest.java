package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PosePlaqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PosePlaque.class);
        PosePlaque posePlaque1 = new PosePlaque();
        posePlaque1.setId(1L);
        PosePlaque posePlaque2 = new PosePlaque();
        posePlaque2.setId(posePlaque1.getId());
        assertThat(posePlaque1).isEqualTo(posePlaque2);
        posePlaque2.setId(2L);
        assertThat(posePlaque1).isNotEqualTo(posePlaque2);
        posePlaque1.setId(null);
        assertThat(posePlaque1).isNotEqualTo(posePlaque2);
    }
}
