package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CommandeCarnetSoucheDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommandeCarnetSoucheDTO.class);
        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO1 = new CommandeCarnetSoucheDTO();
        commandeCarnetSoucheDTO1.setId(1L);
        CommandeCarnetSoucheDTO commandeCarnetSoucheDTO2 = new CommandeCarnetSoucheDTO();
        assertThat(commandeCarnetSoucheDTO1).isNotEqualTo(commandeCarnetSoucheDTO2);
        commandeCarnetSoucheDTO2.setId(commandeCarnetSoucheDTO1.getId());
        assertThat(commandeCarnetSoucheDTO1).isEqualTo(commandeCarnetSoucheDTO2);
        commandeCarnetSoucheDTO2.setId(2L);
        assertThat(commandeCarnetSoucheDTO1).isNotEqualTo(commandeCarnetSoucheDTO2);
        commandeCarnetSoucheDTO1.setId(null);
        assertThat(commandeCarnetSoucheDTO1).isNotEqualTo(commandeCarnetSoucheDTO2);
    }
}
