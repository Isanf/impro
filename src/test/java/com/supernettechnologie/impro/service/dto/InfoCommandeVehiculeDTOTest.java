package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class InfoCommandeVehiculeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoCommandeVehiculeDTO.class);
        InfoCommandeVehiculeDTO infoCommandeVehiculeDTO1 = new InfoCommandeVehiculeDTO();
        infoCommandeVehiculeDTO1.setId(1L);
        InfoCommandeVehiculeDTO infoCommandeVehiculeDTO2 = new InfoCommandeVehiculeDTO();
        assertThat(infoCommandeVehiculeDTO1).isNotEqualTo(infoCommandeVehiculeDTO2);
        infoCommandeVehiculeDTO2.setId(infoCommandeVehiculeDTO1.getId());
        assertThat(infoCommandeVehiculeDTO1).isEqualTo(infoCommandeVehiculeDTO2);
        infoCommandeVehiculeDTO2.setId(2L);
        assertThat(infoCommandeVehiculeDTO1).isNotEqualTo(infoCommandeVehiculeDTO2);
        infoCommandeVehiculeDTO1.setId(null);
        assertThat(infoCommandeVehiculeDTO1).isNotEqualTo(infoCommandeVehiculeDTO2);
    }
}
