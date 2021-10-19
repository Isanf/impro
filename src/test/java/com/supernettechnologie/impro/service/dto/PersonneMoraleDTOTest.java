package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PersonneMoraleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonneMoraleDTO.class);
        PersonneMoraleDTO personneMoraleDTO1 = new PersonneMoraleDTO();
        personneMoraleDTO1.setId(1L);
        PersonneMoraleDTO personneMoraleDTO2 = new PersonneMoraleDTO();
        assertThat(personneMoraleDTO1).isNotEqualTo(personneMoraleDTO2);
        personneMoraleDTO2.setId(personneMoraleDTO1.getId());
        assertThat(personneMoraleDTO1).isEqualTo(personneMoraleDTO2);
        personneMoraleDTO2.setId(2L);
        assertThat(personneMoraleDTO1).isNotEqualTo(personneMoraleDTO2);
        personneMoraleDTO1.setId(null);
        assertThat(personneMoraleDTO1).isNotEqualTo(personneMoraleDTO2);
    }
}
