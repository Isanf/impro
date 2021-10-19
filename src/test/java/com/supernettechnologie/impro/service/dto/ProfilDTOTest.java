package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class ProfilDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfilDTO.class);
        ProfilDTO profilDTO1 = new ProfilDTO();
        profilDTO1.setId(1L);
        ProfilDTO profilDTO2 = new ProfilDTO();
        assertThat(profilDTO1).isNotEqualTo(profilDTO2);
        profilDTO2.setId(profilDTO1.getId());
        assertThat(profilDTO1).isEqualTo(profilDTO2);
        profilDTO2.setId(2L);
        assertThat(profilDTO1).isNotEqualTo(profilDTO2);
        profilDTO1.setId(null);
        assertThat(profilDTO1).isNotEqualTo(profilDTO2);
    }
}
