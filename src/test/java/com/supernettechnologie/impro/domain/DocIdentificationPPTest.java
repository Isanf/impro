package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class DocIdentificationPPTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocIdentificationPP.class);
        DocIdentificationPP docIdentificationPP1 = new DocIdentificationPP();
        docIdentificationPP1.setId(1L);
        DocIdentificationPP docIdentificationPP2 = new DocIdentificationPP();
        docIdentificationPP2.setId(docIdentificationPP1.getId());
        assertThat(docIdentificationPP1).isEqualTo(docIdentificationPP2);
        docIdentificationPP2.setId(2L);
        assertThat(docIdentificationPP1).isNotEqualTo(docIdentificationPP2);
        docIdentificationPP1.setId(null);
        assertThat(docIdentificationPP1).isNotEqualTo(docIdentificationPP2);
    }
}
