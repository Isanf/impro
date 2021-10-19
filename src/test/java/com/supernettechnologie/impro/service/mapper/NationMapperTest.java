package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NationMapperTest {

    private NationMapper nationMapper;

    @BeforeEach
    public void setUp() {
        nationMapper = new NationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(nationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(nationMapper.fromId(null)).isNull();
    }
}
