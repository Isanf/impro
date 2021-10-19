package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class VehiculeTraversantDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeTraversantDTO.class);
        VehiculeTraversantDTO vehiculeTraversantDTO1 = new VehiculeTraversantDTO();
        vehiculeTraversantDTO1.setId(1L);
        VehiculeTraversantDTO vehiculeTraversantDTO2 = new VehiculeTraversantDTO();
        assertThat(vehiculeTraversantDTO1).isNotEqualTo(vehiculeTraversantDTO2);
        vehiculeTraversantDTO2.setId(vehiculeTraversantDTO1.getId());
        assertThat(vehiculeTraversantDTO1).isEqualTo(vehiculeTraversantDTO2);
        vehiculeTraversantDTO2.setId(2L);
        assertThat(vehiculeTraversantDTO1).isNotEqualTo(vehiculeTraversantDTO2);
        vehiculeTraversantDTO1.setId(null);
        assertThat(vehiculeTraversantDTO1).isNotEqualTo(vehiculeTraversantDTO2);
    }
}
