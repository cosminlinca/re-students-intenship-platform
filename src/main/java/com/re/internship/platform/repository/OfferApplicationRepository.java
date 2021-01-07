package com.re.internship.platform.repository;

import com.re.internship.platform.domain.OfferApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OfferApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferApplicationRepository extends JpaRepository<OfferApplication, Long> {
}
