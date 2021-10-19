package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PersonneMoraleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonneMorale.class);
        PersonneMorale personneMorale1 = new PersonneMorale();
        personneMorale1.setId(1L);
        PersonneMorale personneMorale2 = new PersonneMorale();
        personneMorale2.setId(personneMorale1.getId());
        assertThat(personneMorale1).isEqualTo(personneMorale2);
        personneMorale2.setId(2L);
        assertThat(personneMorale1).isNotEqualTo(personneMorale2);
        personneMorale1.setId(null);
        assertThat(personneMorale1).isNotEqualTo(personneMorale2);
    }
}
