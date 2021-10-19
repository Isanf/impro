package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class MarqueVehiculeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarqueVehiculeDTO.class);
        MarqueVehiculeDTO marqueVehiculeDTO1 = new MarqueVehiculeDTO();
        marqueVehiculeDTO1.setId(1L);
        MarqueVehiculeDTO marqueVehiculeDTO2 = new MarqueVehiculeDTO();
        assertThat(marqueVehiculeDTO1).isNotEqualTo(marqueVehiculeDTO2);
        marqueVehiculeDTO2.setId(marqueVehiculeDTO1.getId());
        assertThat(marqueVehiculeDTO1).isEqualTo(marqueVehiculeDTO2);
        marqueVehiculeDTO2.setId(2L);
        assertThat(marqueVehiculeDTO1).isNotEqualTo(marqueVehiculeDTO2);
        marqueVehiculeDTO1.setId(null);
        assertThat(marqueVehiculeDTO1).isNotEqualTo(marqueVehiculeDTO2);
    }
}
