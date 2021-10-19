package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PosePlaqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PosePlaqueDTO.class);
        PosePlaqueDTO posePlaqueDTO1 = new PosePlaqueDTO();
        posePlaqueDTO1.setId(1L);
        PosePlaqueDTO posePlaqueDTO2 = new PosePlaqueDTO();
        assertThat(posePlaqueDTO1).isNotEqualTo(posePlaqueDTO2);
        posePlaqueDTO2.setId(posePlaqueDTO1.getId());
        assertThat(posePlaqueDTO1).isEqualTo(posePlaqueDTO2);
        posePlaqueDTO2.setId(2L);
        assertThat(posePlaqueDTO1).isNotEqualTo(posePlaqueDTO2);
        posePlaqueDTO1.setId(null);
        assertThat(posePlaqueDTO1).isNotEqualTo(posePlaqueDTO2);
    }
}
