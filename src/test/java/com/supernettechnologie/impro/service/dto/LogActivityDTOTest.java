package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class LogActivityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogActivityDTO.class);
        LogActivityDTO logActivityDTO1 = new LogActivityDTO();
        logActivityDTO1.setId(1L);
        LogActivityDTO logActivityDTO2 = new LogActivityDTO();
        assertThat(logActivityDTO1).isNotEqualTo(logActivityDTO2);
        logActivityDTO2.setId(logActivityDTO1.getId());
        assertThat(logActivityDTO1).isEqualTo(logActivityDTO2);
        logActivityDTO2.setId(2L);
        assertThat(logActivityDTO1).isNotEqualTo(logActivityDTO2);
        logActivityDTO1.setId(null);
        assertThat(logActivityDTO1).isNotEqualTo(logActivityDTO2);
    }
}
