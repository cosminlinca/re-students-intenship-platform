package com.re.internship.platform.repository;

import com.re.internship.platform.domain.Offer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Offer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {}
