package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeVehiculeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeVehicule.class);
        TypeVehicule typeVehicule1 = new TypeVehicule();
        typeVehicule1.setId(1L);
        TypeVehicule typeVehicule2 = new TypeVehicule();
        typeVehicule2.setId(typeVehicule1.getId());
        assertThat(typeVehicule1).isEqualTo(typeVehicule2);
        typeVehicule2.setId(2L);
        assertThat(typeVehicule1).isNotEqualTo(typeVehicule2);
        typeVehicule1.setId(null);
        assertThat(typeVehicule1).isNotEqualTo(typeVehicule2);
    }
}
