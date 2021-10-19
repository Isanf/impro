package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocIdentificationPPMapperTest {

    private DocIdentificationPPMapper docIdentificationPPMapper;

    @BeforeEach
    public void setUp() {
        docIdentificationPPMapper = new DocIdentificationPPMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(docIdentificationPPMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(docIdentificationPPMapper.fromId(null)).isNull();
    }
}
