package com.supernettechnologie.impro.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.supernettechnologie.impro.web.rest.TestUtil;

public class FirstloginTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Firstlogin.class);
        Firstlogin firstlogin1 = new Firstlogin();
        firstlogin1.setId(1L);
        Firstlogin firstlogin2 = new Firstlogin();
        firstlogin2.setId(firstlogin1.getId());
        assertThat(firstlogin1).isEqualTo(firstlogin2);
        firstlogin2.setId(2L);
        assertThat(firstlogin1).isNotEqualTo(firstlogin2);
        firstlogin1.setId(null);
        assertThat(firstlogin1).isNotEqualTo(firstlogin2);
    }
}
