package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CertificatImmatriculationMapperTest {

    private CertificatImmatriculationMapper certificatImmatriculationMapper;

    @BeforeEach
    public void setUp() {
        certificatImmatriculationMapper = new CertificatImmatriculationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(certificatImmatriculationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(certificatImmatriculationMapper.fromId(null)).isNull();
    }
}
