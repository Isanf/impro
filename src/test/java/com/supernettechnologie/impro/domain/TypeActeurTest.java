package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeActeurTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeActeur.class);
        TypeActeur typeActeur1 = new TypeActeur();
        typeActeur1.setId(1L);
        TypeActeur typeActeur2 = new TypeActeur();
        typeActeur2.setId(typeActeur1.getId());
        assertThat(typeActeur1).isEqualTo(typeActeur2);
        typeActeur2.setId(2L);
        assertThat(typeActeur1).isNotEqualTo(typeActeur2);
        typeActeur1.setId(null);
        assertThat(typeActeur1).isNotEqualTo(typeActeur2);
    }
}
