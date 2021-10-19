package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CertificatImmatriculationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificatImmatriculation.class);
        CertificatImmatriculation certificatImmatriculation1 = new CertificatImmatriculation();
        certificatImmatriculation1.setId(1L);
        CertificatImmatriculation certificatImmatriculation2 = new CertificatImmatriculation();
        certificatImmatriculation2.setId(certificatImmatriculation1.getId());
        assertThat(certificatImmatriculation1).isEqualTo(certificatImmatriculation2);
        certificatImmatriculation2.setId(2L);
        assertThat(certificatImmatriculation1).isNotEqualTo(certificatImmatriculation2);
        certificatImmatriculation1.setId(null);
        assertThat(certificatImmatriculation1).isNotEqualTo(certificatImmatriculation2);
    }
}
