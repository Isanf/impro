package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserDeviceIdMapperTest {

    private UserDeviceIdMapper userDeviceIdMapper;

    @BeforeEach
    public void setUp() {
        userDeviceIdMapper = new UserDeviceIdMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userDeviceIdMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userDeviceIdMapper.fromId(null)).isNull();
    }
}
