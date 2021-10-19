package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class UserOtpTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserOtp.class);
        UserOtp userOtp1 = new UserOtp();
        userOtp1.setId(1L);
        UserOtp userOtp2 = new UserOtp();
        userOtp2.setId(userOtp1.getId());
        assertThat(userOtp1).isEqualTo(userOtp2);
        userOtp2.setId(2L);
        assertThat(userOtp1).isNotEqualTo(userOtp2);
        userOtp1.setId(null);
        assertThat(userOtp1).isNotEqualTo(userOtp2);
    }
}
