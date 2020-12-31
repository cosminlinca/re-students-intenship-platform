package com.re.internship.platform.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OfferMapperTest {
    private OfferMapper offerMapper;

    @BeforeEach
    public void setUp() {
        offerMapper = new OfferMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(offerMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(offerMapper.fromId(null)).isNull();
    }
}
