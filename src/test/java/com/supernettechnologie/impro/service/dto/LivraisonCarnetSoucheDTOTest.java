package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class LivraisonCarnetSoucheDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivraisonCarnetSoucheDTO.class);
        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO1 = new LivraisonCarnetSoucheDTO();
        livraisonCarnetSoucheDTO1.setId(1L);
        LivraisonCarnetSoucheDTO livraisonCarnetSoucheDTO2 = new LivraisonCarnetSoucheDTO();
        assertThat(livraisonCarnetSoucheDTO1).isNotEqualTo(livraisonCarnetSoucheDTO2);
        livraisonCarnetSoucheDTO2.setId(livraisonCarnetSoucheDTO1.getId());
        assertThat(livraisonCarnetSoucheDTO1).isEqualTo(livraisonCarnetSoucheDTO2);
        livraisonCarnetSoucheDTO2.setId(2L);
        assertThat(livraisonCarnetSoucheDTO1).isNotEqualTo(livraisonCarnetSoucheDTO2);
        livraisonCarnetSoucheDTO1.setId(null);
        assertThat(livraisonCarnetSoucheDTO1).isNotEqualTo(livraisonCarnetSoucheDTO2);
    }
}
