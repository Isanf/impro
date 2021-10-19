package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class NationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nation.class);
        Nation nation1 = new Nation();
        nation1.setId(1L);
        Nation nation2 = new Nation();
        nation2.setId(nation1.getId());
        assertThat(nation1).isEqualTo(nation2);
        nation2.setId(2L);
        assertThat(nation1).isNotEqualTo(nation2);
        nation1.setId(null);
        assertThat(nation1).isNotEqualTo(nation2);
    }
}
