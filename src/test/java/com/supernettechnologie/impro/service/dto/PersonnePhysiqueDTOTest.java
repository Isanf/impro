package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class PersonnePhysiqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonnePhysiqueDTO.class);
        PersonnePhysiqueDTO personnePhysiqueDTO1 = new PersonnePhysiqueDTO();
        personnePhysiqueDTO1.setId(1L);
        PersonnePhysiqueDTO personnePhysiqueDTO2 = new PersonnePhysiqueDTO();
        assertThat(personnePhysiqueDTO1).isNotEqualTo(personnePhysiqueDTO2);
        personnePhysiqueDTO2.setId(personnePhysiqueDTO1.getId());
        assertThat(personnePhysiqueDTO1).isEqualTo(personnePhysiqueDTO2);
        personnePhysiqueDTO2.setId(2L);
        assertThat(personnePhysiqueDTO1).isNotEqualTo(personnePhysiqueDTO2);
        personnePhysiqueDTO1.setId(null);
        assertThat(personnePhysiqueDTO1).isNotEqualTo(personnePhysiqueDTO2);
    }
}
