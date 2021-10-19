package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class NationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NationDTO.class);
        NationDTO nationDTO1 = new NationDTO();
        nationDTO1.setId(1L);
        NationDTO nationDTO2 = new NationDTO();
        assertThat(nationDTO1).isNotEqualTo(nationDTO2);
        nationDTO2.setId(nationDTO1.getId());
        assertThat(nationDTO1).isEqualTo(nationDTO2);
        nationDTO2.setId(2L);
        assertThat(nationDTO1).isNotEqualTo(nationDTO2);
        nationDTO1.setId(null);
        assertThat(nationDTO1).isNotEqualTo(nationDTO2);
    }
}
