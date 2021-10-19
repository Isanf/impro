package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PrixCertificatDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrixCertificatDTO.class);
        PrixCertificatDTO prixCertificatDTO1 = new PrixCertificatDTO();
        prixCertificatDTO1.setId(1L);
        PrixCertificatDTO prixCertificatDTO2 = new PrixCertificatDTO();
        assertThat(prixCertificatDTO1).isNotEqualTo(prixCertificatDTO2);
        prixCertificatDTO2.setId(prixCertificatDTO1.getId());
        assertThat(prixCertificatDTO1).isEqualTo(prixCertificatDTO2);
        prixCertificatDTO2.setId(2L);
        assertThat(prixCertificatDTO1).isNotEqualTo(prixCertificatDTO2);
        prixCertificatDTO1.setId(null);
        assertThat(prixCertificatDTO1).isNotEqualTo(prixCertificatDTO2);
    }
}
