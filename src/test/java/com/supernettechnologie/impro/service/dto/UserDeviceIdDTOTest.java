package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class UserDeviceIdDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDeviceIdDTO.class);
        UserDeviceIdDTO userDeviceIdDTO1 = new UserDeviceIdDTO();
        userDeviceIdDTO1.setId(1L);
        UserDeviceIdDTO userDeviceIdDTO2 = new UserDeviceIdDTO();
        assertThat(userDeviceIdDTO1).isNotEqualTo(userDeviceIdDTO2);
        userDeviceIdDTO2.setId(userDeviceIdDTO1.getId());
        assertThat(userDeviceIdDTO1).isEqualTo(userDeviceIdDTO2);
        userDeviceIdDTO2.setId(2L);
        assertThat(userDeviceIdDTO1).isNotEqualTo(userDeviceIdDTO2);
        userDeviceIdDTO1.setId(null);
        assertThat(userDeviceIdDTO1).isNotEqualTo(userDeviceIdDTO2);
    }
}
