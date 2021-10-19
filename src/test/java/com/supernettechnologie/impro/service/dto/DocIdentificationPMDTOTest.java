package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class DocIdentificationPMDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocIdentificationPMDTO.class);
        DocIdentificationPMDTO docIdentificationPMDTO1 = new DocIdentificationPMDTO();
        docIdentificationPMDTO1.setId(1L);
        DocIdentificationPMDTO docIdentificationPMDTO2 = new DocIdentificationPMDTO();
        assertThat(docIdentificationPMDTO1).isNotEqualTo(docIdentificationPMDTO2);
        docIdentificationPMDTO2.setId(docIdentificationPMDTO1.getId());
        assertThat(docIdentificationPMDTO1).isEqualTo(docIdentificationPMDTO2);
        docIdentificationPMDTO2.setId(2L);
        assertThat(docIdentificationPMDTO1).isNotEqualTo(docIdentificationPMDTO2);
        docIdentificationPMDTO1.setId(null);
        assertThat(docIdentificationPMDTO1).isNotEqualTo(docIdentificationPMDTO2);
    }
}
