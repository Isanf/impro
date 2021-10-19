package com.supernettechnologie.impro.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PrixCertificatMapperTest {

    private PrixCertificatMapper prixCertificatMapper;

    @BeforeEach
    public void setUp() {
        prixCertificatMapper = new PrixCertificatMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(prixCertificatMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(prixCertificatMapper.fromId(null)).isNull();
    }
}
