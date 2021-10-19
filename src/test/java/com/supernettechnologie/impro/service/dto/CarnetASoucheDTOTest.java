package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CarnetASoucheDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CarnetASoucheDTO.class);
        CarnetASoucheDTO carnetASoucheDTO1 = new CarnetASoucheDTO();
        carnetASoucheDTO1.setId(1L);
        CarnetASoucheDTO carnetASoucheDTO2 = new CarnetASoucheDTO();
        assertThat(carnetASoucheDTO1).isNotEqualTo(carnetASoucheDTO2);
        carnetASoucheDTO2.setId(carnetASoucheDTO1.getId());
        assertThat(carnetASoucheDTO1).isEqualTo(carnetASoucheDTO2);
        carnetASoucheDTO2.setId(2L);
        assertThat(carnetASoucheDTO1).isNotEqualTo(carnetASoucheDTO2);
        carnetASoucheDTO1.setId(null);
        assertThat(carnetASoucheDTO1).isNotEqualTo(carnetASoucheDTO2);
    }
}
