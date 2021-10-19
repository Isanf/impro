package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class LogActivityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogActivity.class);
        LogActivity logActivity1 = new LogActivity();
        logActivity1.setId(1L);
        LogActivity logActivity2 = new LogActivity();
        logActivity2.setId(logActivity1.getId());
        assertThat(logActivity1).isEqualTo(logActivity2);
        logActivity2.setId(2L);
        assertThat(logActivity1).isNotEqualTo(logActivity2);
        logActivity1.setId(null);
        assertThat(logActivity1).isNotEqualTo(logActivity2);
    }
}
