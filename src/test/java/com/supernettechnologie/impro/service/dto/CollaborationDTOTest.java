package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class CollaborationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollaborationDTO.class);
        CollaborationDTO collaborationDTO1 = new CollaborationDTO();
        collaborationDTO1.setId(1L);
        CollaborationDTO collaborationDTO2 = new CollaborationDTO();
        assertThat(collaborationDTO1).isNotEqualTo(collaborationDTO2);
        collaborationDTO2.setId(collaborationDTO1.getId());
        assertThat(collaborationDTO1).isEqualTo(collaborationDTO2);
        collaborationDTO2.setId(2L);
        assertThat(collaborationDTO1).isNotEqualTo(collaborationDTO2);
        collaborationDTO1.setId(null);
        assertThat(collaborationDTO1).isNotEqualTo(collaborationDTO2);
    }
}
