package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PrixCertificatTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrixCertificat.class);
        PrixCertificat prixCertificat1 = new PrixCertificat();
        prixCertificat1.setId(1L);
        PrixCertificat prixCertificat2 = new PrixCertificat();
        prixCertificat2.setId(prixCertificat1.getId());
        assertThat(prixCertificat1).isEqualTo(prixCertificat2);
        prixCertificat2.setId(2L);
        assertThat(prixCertificat1).isNotEqualTo(prixCertificat2);
        prixCertificat1.setId(null);
        assertThat(prixCertificat1).isNotEqualTo(prixCertificat2);
    }
}
