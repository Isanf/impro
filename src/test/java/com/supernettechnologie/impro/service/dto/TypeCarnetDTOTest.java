package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeCarnetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeCarnetDTO.class);
        TypeCarnetDTO typeCarnetDTO1 = new TypeCarnetDTO();
        typeCarnetDTO1.setId(1L);
        TypeCarnetDTO typeCarnetDTO2 = new TypeCarnetDTO();
        assertThat(typeCarnetDTO1).isNotEqualTo(typeCarnetDTO2);
        typeCarnetDTO2.setId(typeCarnetDTO1.getId());
        assertThat(typeCarnetDTO1).isEqualTo(typeCarnetDTO2);
        typeCarnetDTO2.setId(2L);
        assertThat(typeCarnetDTO1).isNotEqualTo(typeCarnetDTO2);
        typeCarnetDTO1.setId(null);
        assertThat(typeCarnetDTO1).isNotEqualTo(typeCarnetDTO2);
    }
}
