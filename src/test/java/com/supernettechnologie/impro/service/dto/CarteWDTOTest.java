package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CarteWDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarteWDTO.class);
        CarteWDTO carteWDTO1 = new CarteWDTO();
        carteWDTO1.setId(1L);
        CarteWDTO carteWDTO2 = new CarteWDTO();
        assertThat(carteWDTO1).isNotEqualTo(carteWDTO2);
        carteWDTO2.setId(carteWDTO1.getId());
        assertThat(carteWDTO1).isEqualTo(carteWDTO2);
        carteWDTO2.setId(2L);
        assertThat(carteWDTO1).isNotEqualTo(carteWDTO2);
        carteWDTO1.setId(null);
        assertThat(carteWDTO1).isNotEqualTo(carteWDTO2);
    }
}
