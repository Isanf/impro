package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class FirstloginDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FirstloginDTO.class);
        FirstloginDTO firstloginDTO1 = new FirstloginDTO();
        firstloginDTO1.setId(1L);
        FirstloginDTO firstloginDTO2 = new FirstloginDTO();
        assertThat(firstloginDTO1).isNotEqualTo(firstloginDTO2);
        firstloginDTO2.setId(firstloginDTO1.getId());
        assertThat(firstloginDTO1).isEqualTo(firstloginDTO2);
        firstloginDTO2.setId(2L);
        assertThat(firstloginDTO1).isNotEqualTo(firstloginDTO2);
        firstloginDTO1.setId(null);
        assertThat(firstloginDTO1).isNotEqualTo(firstloginDTO2);
    }
}
