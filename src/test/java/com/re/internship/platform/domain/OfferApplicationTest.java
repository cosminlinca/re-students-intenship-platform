package com.re.internship.platform.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.re.internship.platform.web.rest.TestUtil;

public class OfferApplicationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfferApplication.class);
        OfferApplication offerApplication1 = new OfferApplication();
        offerApplication1.setId(1L);
        OfferApplication offerApplication2 = new OfferApplication();
        offerApplication2.setId(offerApplication1.getId());
        assertThat(offerApplication1).isEqualTo(offerApplication2);
        offerApplication2.setId(2L);
        assertThat(offerApplication1).isNotEqualTo(offerApplication2);
        offerApplication1.setId(null);
        assertThat(offerApplication1).isNotEqualTo(offerApplication2);
    }
}
