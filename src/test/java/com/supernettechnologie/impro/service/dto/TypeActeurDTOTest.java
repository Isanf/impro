package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class TypeActeurDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeActeurDTO.class);
        TypeActeurDTO typeActeurDTO1 = new TypeActeurDTO();
        typeActeurDTO1.setId(1L);
        TypeActeurDTO typeActeurDTO2 = new TypeActeurDTO();
        assertThat(typeActeurDTO1).isNotEqualTo(typeActeurDTO2);
        typeActeurDTO2.setId(typeActeurDTO1.getId());
        assertThat(typeActeurDTO1).isEqualTo(typeActeurDTO2);
        typeActeurDTO2.setId(2L);
        assertThat(typeActeurDTO1).isNotEqualTo(typeActeurDTO2);
        typeActeurDTO1.setId(null);
        assertThat(typeActeurDTO1).isNotEqualTo(typeActeurDTO2);
    }
}
