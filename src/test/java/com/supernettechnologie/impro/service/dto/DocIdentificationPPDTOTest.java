package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class DocIdentificationPPDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocIdentificationPPDTO.class);
        DocIdentificationPPDTO docIdentificationPPDTO1 = new DocIdentificationPPDTO();
        docIdentificationPPDTO1.setId(1L);
        DocIdentificationPPDTO docIdentificationPPDTO2 = new DocIdentificationPPDTO();
        assertThat(docIdentificationPPDTO1).isNotEqualTo(docIdentificationPPDTO2);
        docIdentificationPPDTO2.setId(docIdentificationPPDTO1.getId());
        assertThat(docIdentificationPPDTO1).isEqualTo(docIdentificationPPDTO2);
        docIdentificationPPDTO2.setId(2L);
        assertThat(docIdentificationPPDTO1).isNotEqualTo(docIdentificationPPDTO2);
        docIdentificationPPDTO1.setId(null);
        assertThat(docIdentificationPPDTO1).isNotEqualTo(docIdentificationPPDTO2);
    }
}
