package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DocIdentificationPMMapperTest {

    private DocIdentificationPMMapper docIdentificationPMMapper;

    @BeforeEach
    public void setUp() {
        docIdentificationPMMapper = new DocIdentificationPMMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(docIdentificationPMMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(docIdentificationPMMapper.fromId(null)).isNull();
    }
}
