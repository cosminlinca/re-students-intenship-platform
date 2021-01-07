package com.re.internship.platform.service;

import com.re.internship.platform.domain.OfferApplication;
import com.re.internship.platform.repository.OfferApplicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link OfferApplication}.
 */
@Service
@Transactional
public class OfferApplicationService {

    private final Logger log = LoggerFactory.getLogger(OfferApplicationService.class);

    private final OfferApplicationRepository offerApplicationRepository;

    public OfferApplicationService(OfferApplicationRepository offerApplicationRepository) {
        this.offerApplicationRepository = offerApplicationRepository;
    }

    /**
     * Save a offerApplication.
     *
     * @param offerApplication the entity to save.
     * @return the persisted entity.
     */
    public OfferApplication save(OfferApplication offerApplication) {
        log.debug("Request to save OfferApplication : {}", offerApplication);
        return offerApplicationRepository.save(offerApplication);
    }

    /**
     * Get all the offerApplications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OfferApplication> findAll(Pageable pageable) {
        log.debug("Request to get all OfferApplications");
        return offerApplicationRepository.findAll(pageable);
    }


    /**
     * Get one offerApplication by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OfferApplication> findOne(Long id) {
        log.debug("Request to get OfferApplication : {}", id);
        return offerApplicationRepository.findById(id);
    }

    /**
     * Delete the offerApplication by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OfferApplication : {}", id);
        offerApplicationRepository.deleteById(id);
    }
}
