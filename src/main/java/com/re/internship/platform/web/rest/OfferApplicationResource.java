package com.re.internship.platform.web.rest;

import com.re.internship.platform.domain.OfferApplication;
import com.re.internship.platform.service.OfferApplicationService;
import com.re.internship.platform.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.re.internship.platform.domain.OfferApplication}.
 */
@RestController
@RequestMapping("/api")
public class OfferApplicationResource {

    private final Logger log = LoggerFactory.getLogger(OfferApplicationResource.class);

    private static final String ENTITY_NAME = "offerApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OfferApplicationService offerApplicationService;

    public OfferApplicationResource(OfferApplicationService offerApplicationService) {
        this.offerApplicationService = offerApplicationService;
    }

    /**
     * {@code POST  /offer-applications} : Create a new offerApplication.
     *
     * @param offerApplication the offerApplication to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerApplication, or with status {@code 400 (Bad Request)} if the offerApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offer-applications")
    public ResponseEntity<OfferApplication> createOfferApplication(@RequestBody OfferApplication offerApplication) throws URISyntaxException {
        log.debug("REST request to save OfferApplication : {}", offerApplication);
        if (offerApplication.getId() != null) {
            throw new BadRequestAlertException("A new offerApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferApplication result = offerApplicationService.save(offerApplication);
        return ResponseEntity.created(new URI("/api/offer-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offer-applications} : Updates an existing offerApplication.
     *
     * @param offerApplication the offerApplication to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerApplication,
     * or with status {@code 400 (Bad Request)} if the offerApplication is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerApplication couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offer-applications")
    public ResponseEntity<OfferApplication> updateOfferApplication(@RequestBody OfferApplication offerApplication) throws URISyntaxException {
        log.debug("REST request to update OfferApplication : {}", offerApplication);
        if (offerApplication.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OfferApplication result = offerApplicationService.save(offerApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offerApplication.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offer-applications} : get all the offerApplications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerApplications in body.
     */
    @GetMapping("/offer-applications")
    public ResponseEntity<List<OfferApplication>> getAllOfferApplications(Pageable pageable) {
        log.debug("REST request to get a page of OfferApplications");
        Page<OfferApplication> page = offerApplicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offer-applications/:id} : get the "id" offerApplication.
     *
     * @param id the id of the offerApplication to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerApplication, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offer-applications/{id}")
    public ResponseEntity<OfferApplication> getOfferApplication(@PathVariable Long id) {
        log.debug("REST request to get OfferApplication : {}", id);
        Optional<OfferApplication> offerApplication = offerApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerApplication);
    }

    /**
     * {@code DELETE  /offer-applications/:id} : delete the "id" offerApplication.
     *
     * @param id the id of the offerApplication to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offer-applications/{id}")
    public ResponseEntity<Void> deleteOfferApplication(@PathVariable Long id) {
        log.debug("REST request to delete OfferApplication : {}", id);
        offerApplicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
