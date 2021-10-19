package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class UserDeviceIdTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserDeviceId.class);
        UserDeviceId userDeviceId1 = new UserDeviceId();
        userDeviceId1.setId(1L);
        UserDeviceId userDeviceId2 = new UserDeviceId();
        userDeviceId2.setId(userDeviceId1.getId());
        assertThat(userDeviceId1).isEqualTo(userDeviceId2);
        userDeviceId2.setId(2L);
        assertThat(userDeviceId1).isNotEqualTo(userDeviceId2);
        userDeviceId1.setId(null);
        assertThat(userDeviceId1).isNotEqualTo(userDeviceId2);
    }
}
