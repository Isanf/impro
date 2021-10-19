package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class StatistiqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Statistique.class);
        Statistique statistique1 = new Statistique();
        statistique1.setId(1L);
        Statistique statistique2 = new Statistique();
        statistique2.setId(statistique1.getId());
        assertThat(statistique1).isEqualTo(statistique2);
        statistique2.setId(2L);
        assertThat(statistique1).isNotEqualTo(statistique2);
        statistique1.setId(null);
        assertThat(statistique1).isNotEqualTo(statistique2);
    }
}
