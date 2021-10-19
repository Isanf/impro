package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class InfoCommandeCarnetASoucheDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InfoCommandeCarnetASoucheDTO.class);
        InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO1 = new InfoCommandeCarnetASoucheDTO();
        infoCommandeCarnetASoucheDTO1.setId(1L);
        InfoCommandeCarnetASoucheDTO infoCommandeCarnetASoucheDTO2 = new InfoCommandeCarnetASoucheDTO();
        assertThat(infoCommandeCarnetASoucheDTO1).isNotEqualTo(infoCommandeCarnetASoucheDTO2);
        infoCommandeCarnetASoucheDTO2.setId(infoCommandeCarnetASoucheDTO1.getId());
        assertThat(infoCommandeCarnetASoucheDTO1).isEqualTo(infoCommandeCarnetASoucheDTO2);
        infoCommandeCarnetASoucheDTO2.setId(2L);
        assertThat(infoCommandeCarnetASoucheDTO1).isNotEqualTo(infoCommandeCarnetASoucheDTO2);
        infoCommandeCarnetASoucheDTO1.setId(null);
        assertThat(infoCommandeCarnetASoucheDTO1).isNotEqualTo(infoCommandeCarnetASoucheDTO2);
    }
}
