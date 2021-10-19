package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class VehiculeOccasionalDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehiculeOccasionalDTO.class);
        VehiculeOccasionalDTO vehiculeOccasionalDTO1 = new VehiculeOccasionalDTO();
        vehiculeOccasionalDTO1.setId(1L);
        VehiculeOccasionalDTO vehiculeOccasionalDTO2 = new VehiculeOccasionalDTO();
        assertThat(vehiculeOccasionalDTO1).isNotEqualTo(vehiculeOccasionalDTO2);
        vehiculeOccasionalDTO2.setId(vehiculeOccasionalDTO1.getId());
        assertThat(vehiculeOccasionalDTO1).isEqualTo(vehiculeOccasionalDTO2);
        vehiculeOccasionalDTO2.setId(2L);
        assertThat(vehiculeOccasionalDTO1).isNotEqualTo(vehiculeOccasionalDTO2);
        vehiculeOccasionalDTO1.setId(null);
        assertThat(vehiculeOccasionalDTO1).isNotEqualTo(vehiculeOccasionalDTO2);
    }
}
