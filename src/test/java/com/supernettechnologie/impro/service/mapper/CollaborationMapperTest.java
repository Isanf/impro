package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CollaborationMapperTest {

    private CollaborationMapper collaborationMapper;

    @BeforeEach
    public void setUp() {
        collaborationMapper = new CollaborationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(collaborationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(collaborationMapper.fromId(null)).isNull();
    }
}
