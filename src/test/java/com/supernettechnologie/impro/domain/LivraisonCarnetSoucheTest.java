package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class LivraisonCarnetSoucheTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivraisonCarnetSouche.class);
        LivraisonCarnetSouche livraisonCarnetSouche1 = new LivraisonCarnetSouche();
        livraisonCarnetSouche1.setId(1L);
        LivraisonCarnetSouche livraisonCarnetSouche2 = new LivraisonCarnetSouche();
        livraisonCarnetSouche2.setId(livraisonCarnetSouche1.getId());
        assertThat(livraisonCarnetSouche1).isEqualTo(livraisonCarnetSouche2);
        livraisonCarnetSouche2.setId(2L);
        assertThat(livraisonCarnetSouche1).isNotEqualTo(livraisonCarnetSouche2);
        livraisonCarnetSouche1.setId(null);
        assertThat(livraisonCarnetSouche1).isNotEqualTo(livraisonCarnetSouche2);
    }
}
