package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class StatistiqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatistiqueDTO.class);
        StatistiqueDTO statistiqueDTO1 = new StatistiqueDTO();
        statistiqueDTO1.setId(1L);
        StatistiqueDTO statistiqueDTO2 = new StatistiqueDTO();
        assertThat(statistiqueDTO1).isNotEqualTo(statistiqueDTO2);
        statistiqueDTO2.setId(statistiqueDTO1.getId());
        assertThat(statistiqueDTO1).isEqualTo(statistiqueDTO2);
        statistiqueDTO2.setId(2L);
        assertThat(statistiqueDTO1).isNotEqualTo(statistiqueDTO2);
        statistiqueDTO1.setId(null);
        assertThat(statistiqueDTO1).isNotEqualTo(statistiqueDTO2);
    }
}
