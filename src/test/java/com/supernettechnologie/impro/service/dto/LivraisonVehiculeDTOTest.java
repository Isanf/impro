package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class LivraisonVehiculeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivraisonVehiculeDTO.class);
        LivraisonVehiculeDTO livraisonVehiculeDTO1 = new LivraisonVehiculeDTO();
        livraisonVehiculeDTO1.setId(1L);
        LivraisonVehiculeDTO livraisonVehiculeDTO2 = new LivraisonVehiculeDTO();
        assertThat(livraisonVehiculeDTO1).isNotEqualTo(livraisonVehiculeDTO2);
        livraisonVehiculeDTO2.setId(livraisonVehiculeDTO1.getId());
        assertThat(livraisonVehiculeDTO1).isEqualTo(livraisonVehiculeDTO2);
        livraisonVehiculeDTO2.setId(2L);
        assertThat(livraisonVehiculeDTO1).isNotEqualTo(livraisonVehiculeDTO2);
        livraisonVehiculeDTO1.setId(null);
        assertThat(livraisonVehiculeDTO1).isNotEqualTo(livraisonVehiculeDTO2);
    }
}
