package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PlaqueGarageDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaqueGarageDTO.class);
        PlaqueGarageDTO plaqueGarageDTO1 = new PlaqueGarageDTO();
        plaqueGarageDTO1.setId(1L);
        PlaqueGarageDTO plaqueGarageDTO2 = new PlaqueGarageDTO();
        assertThat(plaqueGarageDTO1).isNotEqualTo(plaqueGarageDTO2);
        plaqueGarageDTO2.setId(plaqueGarageDTO1.getId());
        assertThat(plaqueGarageDTO1).isEqualTo(plaqueGarageDTO2);
        plaqueGarageDTO2.setId(2L);
        assertThat(plaqueGarageDTO1).isNotEqualTo(plaqueGarageDTO2);
        plaqueGarageDTO1.setId(null);
        assertThat(plaqueGarageDTO1).isNotEqualTo(plaqueGarageDTO2);
    }
}
