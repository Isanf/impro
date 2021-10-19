package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CommandeVehiculeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeVehiculeDTO.class);
        CommandeVehiculeDTO commandeVehiculeDTO1 = new CommandeVehiculeDTO();
        commandeVehiculeDTO1.setId(1L);
        CommandeVehiculeDTO commandeVehiculeDTO2 = new CommandeVehiculeDTO();
        assertThat(commandeVehiculeDTO1).isNotEqualTo(commandeVehiculeDTO2);
        commandeVehiculeDTO2.setId(commandeVehiculeDTO1.getId());
        assertThat(commandeVehiculeDTO1).isEqualTo(commandeVehiculeDTO2);
        commandeVehiculeDTO2.setId(2L);
        assertThat(commandeVehiculeDTO1).isNotEqualTo(commandeVehiculeDTO2);
        commandeVehiculeDTO1.setId(null);
        assertThat(commandeVehiculeDTO1).isNotEqualTo(commandeVehiculeDTO2);
    }
}
