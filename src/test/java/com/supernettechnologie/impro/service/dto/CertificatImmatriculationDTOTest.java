package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CertificatImmatriculationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificatImmatriculationDTO.class);
        CertificatImmatriculationDTO certificatImmatriculationDTO1 = new CertificatImmatriculationDTO();
        certificatImmatriculationDTO1.setId(1L);
        CertificatImmatriculationDTO certificatImmatriculationDTO2 = new CertificatImmatriculationDTO();
        assertThat(certificatImmatriculationDTO1).isNotEqualTo(certificatImmatriculationDTO2);
        certificatImmatriculationDTO2.setId(certificatImmatriculationDTO1.getId());
        assertThat(certificatImmatriculationDTO1).isEqualTo(certificatImmatriculationDTO2);
        certificatImmatriculationDTO2.setId(2L);
        assertThat(certificatImmatriculationDTO1).isNotEqualTo(certificatImmatriculationDTO2);
        certificatImmatriculationDTO1.setId(null);
        assertThat(certificatImmatriculationDTO1).isNotEqualTo(certificatImmatriculationDTO2);
    }
}
