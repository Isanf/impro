package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CommandeCarnetSoucheTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeCarnetSouche.class);
        CommandeCarnetSouche commandeCarnetSouche1 = new CommandeCarnetSouche();
        commandeCarnetSouche1.setId(1L);
        CommandeCarnetSouche commandeCarnetSouche2 = new CommandeCarnetSouche();
        commandeCarnetSouche2.setId(commandeCarnetSouche1.getId());
        assertThat(commandeCarnetSouche1).isEqualTo(commandeCarnetSouche2);
        commandeCarnetSouche2.setId(2L);
        assertThat(commandeCarnetSouche1).isNotEqualTo(commandeCarnetSouche2);
        commandeCarnetSouche1.setId(null);
        assertThat(commandeCarnetSouche1).isNotEqualTo(commandeCarnetSouche2);
    }
}
