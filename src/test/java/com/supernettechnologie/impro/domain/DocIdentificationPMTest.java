package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class DocIdentificationPMTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocIdentificationPM.class);
        DocIdentificationPM docIdentificationPM1 = new DocIdentificationPM();
        docIdentificationPM1.setId(1L);
        DocIdentificationPM docIdentificationPM2 = new DocIdentificationPM();
        docIdentificationPM2.setId(docIdentificationPM1.getId());
        assertThat(docIdentificationPM1).isEqualTo(docIdentificationPM2);
        docIdentificationPM2.setId(2L);
        assertThat(docIdentificationPM1).isNotEqualTo(docIdentificationPM2);
        docIdentificationPM1.setId(null);
        assertThat(docIdentificationPM1).isNotEqualTo(docIdentificationPM2);
    }
}
