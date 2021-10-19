package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PlaqueImmatriculationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaqueImmatriculationDTO.class);
        PlaqueImmatriculationDTO plaqueImmatriculationDTO1 = new PlaqueImmatriculationDTO();
        plaqueImmatriculationDTO1.setId(1L);
        PlaqueImmatriculationDTO plaqueImmatriculationDTO2 = new PlaqueImmatriculationDTO();
        assertThat(plaqueImmatriculationDTO1).isNotEqualTo(plaqueImmatriculationDTO2);
        plaqueImmatriculationDTO2.setId(plaqueImmatriculationDTO1.getId());
        assertThat(plaqueImmatriculationDTO1).isEqualTo(plaqueImmatriculationDTO2);
        plaqueImmatriculationDTO2.setId(2L);
        assertThat(plaqueImmatriculationDTO1).isNotEqualTo(plaqueImmatriculationDTO2);
        plaqueImmatriculationDTO1.setId(null);
        assertThat(plaqueImmatriculationDTO1).isNotEqualTo(plaqueImmatriculationDTO2);
    }
}
