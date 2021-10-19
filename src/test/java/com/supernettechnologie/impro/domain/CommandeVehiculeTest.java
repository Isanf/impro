package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CommandeVehiculeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeVehicule.class);
        CommandeVehicule commandeVehicule1 = new CommandeVehicule();
        commandeVehicule1.setId(1L);
        CommandeVehicule commandeVehicule2 = new CommandeVehicule();
        commandeVehicule2.setId(commandeVehicule1.getId());
        assertThat(commandeVehicule1).isEqualTo(commandeVehicule2);
        commandeVehicule2.setId(2L);
        assertThat(commandeVehicule1).isNotEqualTo(commandeVehicule2);
        commandeVehicule1.setId(null);
        assertThat(commandeVehicule1).isNotEqualTo(commandeVehicule2);
    }
}
