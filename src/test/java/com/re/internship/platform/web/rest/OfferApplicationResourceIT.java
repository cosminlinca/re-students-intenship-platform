package com.re.internship.platform.web.rest;

import com.re.internship.platform.StudentsIntenshipPlatformAvraApp;
import com.re.internship.platform.domain.OfferApplication;
import com.re.internship.platform.repository.OfferApplicationRepository;
import com.re.internship.platform.service.OfferApplicationService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OfferApplicationResource} REST controller.
 */
@SpringBootTest(classes = StudentsIntenshipPlatformAvraApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfferApplicationResourceIT {

    private static final Long DEFAULT_ID_STUDENT = 1L;
    private static final Long UPDATED_ID_STUDENT = 2L;

    private static final Long DEFAULT_ID_OFFER = 1L;
    private static final Long UPDATED_ID_OFFER = 2L;

    private static final byte[] DEFAULT_ATTACHED_CV = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHED_CV = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHED_CV_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHED_CV_CONTENT_TYPE = "image/png";

    @Autowired
    private OfferApplicationRepository offerApplicationRepository;

    @Autowired
    private OfferApplicationService offerApplicationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferApplicationMockMvc;

    private OfferApplication offerApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferApplication createEntity(EntityManager em) {
        OfferApplication offerApplication = new OfferApplication()
            .idStudent(DEFAULT_ID_STUDENT)
            .idOffer(DEFAULT_ID_OFFER)
            .attachedCV(DEFAULT_ATTACHED_CV)
            .attachedCVContentType(DEFAULT_ATTACHED_CV_CONTENT_TYPE);
        return offerApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfferApplication createUpdatedEntity(EntityManager em) {
        OfferApplication offerApplication = new OfferApplication()
            .idStudent(UPDATED_ID_STUDENT)
            .idOffer(UPDATED_ID_OFFER)
            .attachedCV(UPDATED_ATTACHED_CV)
            .attachedCVContentType(UPDATED_ATTACHED_CV_CONTENT_TYPE);
        return offerApplication;
    }

    @BeforeEach
    public void initTest() {
        offerApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfferApplication() throws Exception {
        int databaseSizeBeforeCreate = offerApplicationRepository.findAll().size();
        // Create the OfferApplication
        restOfferApplicationMockMvc.perform(post("/api/offer-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerApplication)))
            .andExpect(status().isCreated());

        // Validate the OfferApplication in the database
        List<OfferApplication> offerApplicationList = offerApplicationRepository.findAll();
        assertThat(offerApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        OfferApplication testOfferApplication = offerApplicationList.get(offerApplicationList.size() - 1);
        assertThat(testOfferApplication.getIdStudent()).isEqualTo(DEFAULT_ID_STUDENT);
        assertThat(testOfferApplication.getIdOffer()).isEqualTo(DEFAULT_ID_OFFER);
        assertThat(testOfferApplication.getAttachedCV()).isEqualTo(DEFAULT_ATTACHED_CV);
        assertThat(testOfferApplication.getAttachedCVContentType()).isEqualTo(DEFAULT_ATTACHED_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createOfferApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerApplicationRepository.findAll().size();

        // Create the OfferApplication with an existing ID
        offerApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferApplicationMockMvc.perform(post("/api/offer-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerApplication)))
            .andExpect(status().isBadRequest());

        // Validate the OfferApplication in the database
        List<OfferApplication> offerApplicationList = offerApplicationRepository.findAll();
        assertThat(offerApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOfferApplications() throws Exception {
        // Initialize the database
        offerApplicationRepository.saveAndFlush(offerApplication);

        // Get all the offerApplicationList
        restOfferApplicationMockMvc.perform(get("/api/offer-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offerApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].idStudent").value(hasItem(DEFAULT_ID_STUDENT.intValue())))
            .andExpect(jsonPath("$.[*].idOffer").value(hasItem(DEFAULT_ID_OFFER.intValue())))
            .andExpect(jsonPath("$.[*].attachedCVContentType").value(hasItem(DEFAULT_ATTACHED_CV_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachedCV").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHED_CV))));
    }
    
    @Test
    @Transactional
    public void getOfferApplication() throws Exception {
        // Initialize the database
        offerApplicationRepository.saveAndFlush(offerApplication);

        // Get the offerApplication
        restOfferApplicationMockMvc.perform(get("/api/offer-applications/{id}", offerApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offerApplication.getId().intValue()))
            .andExpect(jsonPath("$.idStudent").value(DEFAULT_ID_STUDENT.intValue()))
            .andExpect(jsonPath("$.idOffer").value(DEFAULT_ID_OFFER.intValue()))
            .andExpect(jsonPath("$.attachedCVContentType").value(DEFAULT_ATTACHED_CV_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachedCV").value(Base64Utils.encodeToString(DEFAULT_ATTACHED_CV)));
    }
    @Test
    @Transactional
    public void getNonExistingOfferApplication() throws Exception {
        // Get the offerApplication
        restOfferApplicationMockMvc.perform(get("/api/offer-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfferApplication() throws Exception {
        // Initialize the database
        offerApplicationService.save(offerApplication);

        int databaseSizeBeforeUpdate = offerApplicationRepository.findAll().size();

        // Update the offerApplication
        OfferApplication updatedOfferApplication = offerApplicationRepository.findById(offerApplication.getId()).get();
        // Disconnect from session so that the updates on updatedOfferApplication are not directly saved in db
        em.detach(updatedOfferApplication);
        updatedOfferApplication
            .idStudent(UPDATED_ID_STUDENT)
            .idOffer(UPDATED_ID_OFFER)
            .attachedCV(UPDATED_ATTACHED_CV)
            .attachedCVContentType(UPDATED_ATTACHED_CV_CONTENT_TYPE);

        restOfferApplicationMockMvc.perform(put("/api/offer-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfferApplication)))
            .andExpect(status().isOk());

        // Validate the OfferApplication in the database
        List<OfferApplication> offerApplicationList = offerApplicationRepository.findAll();
        assertThat(offerApplicationList).hasSize(databaseSizeBeforeUpdate);
        OfferApplication testOfferApplication = offerApplicationList.get(offerApplicationList.size() - 1);
        assertThat(testOfferApplication.getIdStudent()).isEqualTo(UPDATED_ID_STUDENT);
        assertThat(testOfferApplication.getIdOffer()).isEqualTo(UPDATED_ID_OFFER);
        assertThat(testOfferApplication.getAttachedCV()).isEqualTo(UPDATED_ATTACHED_CV);
        assertThat(testOfferApplication.getAttachedCVContentType()).isEqualTo(UPDATED_ATTACHED_CV_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingOfferApplication() throws Exception {
        int databaseSizeBeforeUpdate = offerApplicationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferApplicationMockMvc.perform(put("/api/offer-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offerApplication)))
            .andExpect(status().isBadRequest());

        // Validate the OfferApplication in the database
        List<OfferApplication> offerApplicationList = offerApplicationRepository.findAll();
        assertThat(offerApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfferApplication() throws Exception {
        // Initialize the database
        offerApplicationService.save(offerApplication);

        int databaseSizeBeforeDelete = offerApplicationRepository.findAll().size();

        // Delete the offerApplication
        restOfferApplicationMockMvc.perform(delete("/api/offer-applications/{id}", offerApplication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfferApplication> offerApplicationList = offerApplicationRepository.findAll();
        assertThat(offerApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
