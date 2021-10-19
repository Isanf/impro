package com.supernettechnologie.impro.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class UserOtpDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserOtpDTO.class);
        UserOtpDTO userOtpDTO1 = new UserOtpDTO();
        userOtpDTO1.setId(1L);
        UserOtpDTO userOtpDTO2 = new UserOtpDTO();
        assertThat(userOtpDTO1).isNotEqualTo(userOtpDTO2);
        userOtpDTO2.setId(userOtpDTO1.getId());
        assertThat(userOtpDTO1).isEqualTo(userOtpDTO2);
        userOtpDTO2.setId(2L);
        assertThat(userOtpDTO1).isNotEqualTo(userOtpDTO2);
        userOtpDTO1.setId(null);
        assertThat(userOtpDTO1).isNotEqualTo(userOtpDTO2);
    }
}
