package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserOtpMapperTest {

    private UserOtpMapper userOtpMapper;

    @BeforeEach
    public void setUp() {
        userOtpMapper = new UserOtpMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userOtpMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userOtpMapper.fromId(null)).isNull();
    }
}
