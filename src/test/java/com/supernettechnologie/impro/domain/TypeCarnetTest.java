package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeCarnetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCarnet.class);
        TypeCarnet typeCarnet1 = new TypeCarnet();
        typeCarnet1.setId(1L);
        TypeCarnet typeCarnet2 = new TypeCarnet();
        typeCarnet2.setId(typeCarnet1.getId());
        assertThat(typeCarnet1).isEqualTo(typeCarnet2);
        typeCarnet2.setId(2L);
        assertThat(typeCarnet1).isNotEqualTo(typeCarnet2);
        typeCarnet1.setId(null);
        assertThat(typeCarnet1).isNotEqualTo(typeCarnet2);
    }
}
