package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class ImmatriculationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImmatriculationDTO.class);
        ImmatriculationDTO immatriculationDTO1 = new ImmatriculationDTO();
        immatriculationDTO1.setId(1L);
        ImmatriculationDTO immatriculationDTO2 = new ImmatriculationDTO();
        assertThat(immatriculationDTO1).isNotEqualTo(immatriculationDTO2);
        immatriculationDTO2.setId(immatriculationDTO1.getId());
        assertThat(immatriculationDTO1).isEqualTo(immatriculationDTO2);
        immatriculationDTO2.setId(2L);
        assertThat(immatriculationDTO1).isNotEqualTo(immatriculationDTO2);
        immatriculationDTO1.setId(null);
        assertThat(immatriculationDTO1).isNotEqualTo(immatriculationDTO2);
    }
}
