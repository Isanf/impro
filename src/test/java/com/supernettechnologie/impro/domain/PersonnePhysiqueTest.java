package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PersonnePhysiqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonnePhysique.class);
        PersonnePhysique personnePhysique1 = new PersonnePhysique();
        personnePhysique1.setId(1L);
        PersonnePhysique personnePhysique2 = new PersonnePhysique();
        personnePhysique2.setId(personnePhysique1.getId());
        assertThat(personnePhysique1).isEqualTo(personnePhysique2);
        personnePhysique2.setId(2L);
        assertThat(personnePhysique1).isNotEqualTo(personnePhysique2);
        personnePhysique1.setId(null);
        assertThat(personnePhysique1).isNotEqualTo(personnePhysique2);
    }
}
