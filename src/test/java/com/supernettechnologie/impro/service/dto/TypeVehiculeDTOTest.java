package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeVehiculeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeVehiculeDTO.class);
        TypeVehiculeDTO typeVehiculeDTO1 = new TypeVehiculeDTO();
        typeVehiculeDTO1.setId(1L);
        TypeVehiculeDTO typeVehiculeDTO2 = new TypeVehiculeDTO();
        assertThat(typeVehiculeDTO1).isNotEqualTo(typeVehiculeDTO2);
        typeVehiculeDTO2.setId(typeVehiculeDTO1.getId());
        assertThat(typeVehiculeDTO1).isEqualTo(typeVehiculeDTO2);
        typeVehiculeDTO2.setId(2L);
        assertThat(typeVehiculeDTO1).isNotEqualTo(typeVehiculeDTO2);
        typeVehiculeDTO1.setId(null);
        assertThat(typeVehiculeDTO1).isNotEqualTo(typeVehiculeDTO2);
    }
}
