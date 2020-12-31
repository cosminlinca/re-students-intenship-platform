package com.re.internship.platform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.re.internship.platform.StudentsIntenshipPlatformAvraApp;
import com.re.internship.platform.domain.Offer;
import com.re.internship.platform.repository.OfferRepository;
import com.re.internship.platform.service.OfferQueryService;
import com.re.internship.platform.service.OfferService;
import com.re.internship.platform.service.dto.OfferCriteria;
import com.re.internship.platform.service.dto.OfferDTO;
import com.re.internship.platform.service.mapper.OfferMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OfferResource} REST controller.
 */
@SpringBootTest(classes = StudentsIntenshipPlatformAvraApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OfferResourceIT {
    private static final String DEFAULT_POSITION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_POSITION_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PROGRAM_DURATION_IN_WEEKS = 1;
    private static final Integer UPDATED_PROGRAM_DURATION_IN_WEEKS = 2;
    private static final Integer SMALLER_PROGRAM_DURATION_IN_WEEKS = 1 - 1;

    private static final String DEFAULT_REQUIRED_SKILLS = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRED_SKILLS = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGIES = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGIES = "BBBBBBBBBB";

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PAID = false;
    private static final Boolean UPDATED_PAID = true;

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN = "BBBBBBBBBB";

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferQueryService offerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOfferMockMvc;

    private Offer offer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createEntity(EntityManager em) {
        Offer offer = new Offer()
            .positionName(DEFAULT_POSITION_NAME)
            .programDurationInWeeks(DEFAULT_PROGRAM_DURATION_IN_WEEKS)
            .requiredSkills(DEFAULT_REQUIRED_SKILLS)
            .technologies(DEFAULT_TECHNOLOGIES)
            .details(DEFAULT_DETAILS)
            .paid(DEFAULT_PAID)
            .observations(DEFAULT_OBSERVATIONS)
            .domain(DEFAULT_DOMAIN);
        return offer;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offer createUpdatedEntity(EntityManager em) {
        Offer offer = new Offer()
            .positionName(UPDATED_POSITION_NAME)
            .programDurationInWeeks(UPDATED_PROGRAM_DURATION_IN_WEEKS)
            .requiredSkills(UPDATED_REQUIRED_SKILLS)
            .technologies(UPDATED_TECHNOLOGIES)
            .details(UPDATED_DETAILS)
            .paid(UPDATED_PAID)
            .observations(UPDATED_OBSERVATIONS)
            .domain(UPDATED_DOMAIN);
        return offer;
    }

    @BeforeEach
    public void initTest() {
        offer = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffer() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();
        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);
        restOfferMockMvc
            .perform(post("/api/offers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isCreated());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate + 1);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getPositionName()).isEqualTo(DEFAULT_POSITION_NAME);
        assertThat(testOffer.getProgramDurationInWeeks()).isEqualTo(DEFAULT_PROGRAM_DURATION_IN_WEEKS);
        assertThat(testOffer.getRequiredSkills()).isEqualTo(DEFAULT_REQUIRED_SKILLS);
        assertThat(testOffer.getTechnologies()).isEqualTo(DEFAULT_TECHNOLOGIES);
        assertThat(testOffer.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testOffer.isPaid()).isEqualTo(DEFAULT_PAID);
        assertThat(testOffer.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
        assertThat(testOffer.getDomain()).isEqualTo(DEFAULT_DOMAIN);
    }

    @Test
    @Transactional
    public void createOfferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offerRepository.findAll().size();

        // Create the Offer with an existing ID
        offer.setId(1L);
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfferMockMvc
            .perform(post("/api/offers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOffers() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList
        restOfferMockMvc
            .perform(get("/api/offers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionName").value(hasItem(DEFAULT_POSITION_NAME)))
            .andExpect(jsonPath("$.[*].programDurationInWeeks").value(hasItem(DEFAULT_PROGRAM_DURATION_IN_WEEKS)))
            .andExpect(jsonPath("$.[*].requiredSkills").value(hasItem(DEFAULT_REQUIRED_SKILLS)))
            .andExpect(jsonPath("$.[*].technologies").value(hasItem(DEFAULT_TECHNOLOGIES)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)));
    }

    @Test
    @Transactional
    public void getOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get the offer
        restOfferMockMvc
            .perform(get("/api/offers/{id}", offer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offer.getId().intValue()))
            .andExpect(jsonPath("$.positionName").value(DEFAULT_POSITION_NAME))
            .andExpect(jsonPath("$.programDurationInWeeks").value(DEFAULT_PROGRAM_DURATION_IN_WEEKS))
            .andExpect(jsonPath("$.requiredSkills").value(DEFAULT_REQUIRED_SKILLS))
            .andExpect(jsonPath("$.technologies").value(DEFAULT_TECHNOLOGIES))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS))
            .andExpect(jsonPath("$.paid").value(DEFAULT_PAID.booleanValue()))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS))
            .andExpect(jsonPath("$.domain").value(DEFAULT_DOMAIN));
    }

    @Test
    @Transactional
    public void getOffersByIdFiltering() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        Long id = offer.getId();

        defaultOfferShouldBeFound("id.equals=" + id);
        defaultOfferShouldNotBeFound("id.notEquals=" + id);

        defaultOfferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOfferShouldNotBeFound("id.greaterThan=" + id);

        defaultOfferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOfferShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllOffersByPositionNameIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where positionName equals to DEFAULT_POSITION_NAME
        defaultOfferShouldBeFound("positionName.equals=" + DEFAULT_POSITION_NAME);

        // Get all the offerList where positionName equals to UPDATED_POSITION_NAME
        defaultOfferShouldNotBeFound("positionName.equals=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllOffersByPositionNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where positionName not equals to DEFAULT_POSITION_NAME
        defaultOfferShouldNotBeFound("positionName.notEquals=" + DEFAULT_POSITION_NAME);

        // Get all the offerList where positionName not equals to UPDATED_POSITION_NAME
        defaultOfferShouldBeFound("positionName.notEquals=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllOffersByPositionNameIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where positionName in DEFAULT_POSITION_NAME or UPDATED_POSITION_NAME
        defaultOfferShouldBeFound("positionName.in=" + DEFAULT_POSITION_NAME + "," + UPDATED_POSITION_NAME);

        // Get all the offerList where positionName equals to UPDATED_POSITION_NAME
        defaultOfferShouldNotBeFound("positionName.in=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllOffersByPositionNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where positionName is not null
        defaultOfferShouldBeFound("positionName.specified=true");

        // Get all the offerList where positionName is null
        defaultOfferShouldNotBeFound("positionName.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByPositionNameContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where positionName contains DEFAULT_POSITION_NAME
        defaultOfferShouldBeFound("positionName.contains=" + DEFAULT_POSITION_NAME);

        // Get all the offerList where positionName contains UPDATED_POSITION_NAME
        defaultOfferShouldNotBeFound("positionName.contains=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllOffersByPositionNameNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where positionName does not contain DEFAULT_POSITION_NAME
        defaultOfferShouldNotBeFound("positionName.doesNotContain=" + DEFAULT_POSITION_NAME);

        // Get all the offerList where positionName does not contain UPDATED_POSITION_NAME
        defaultOfferShouldBeFound("positionName.doesNotContain=" + UPDATED_POSITION_NAME);
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks equals to DEFAULT_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldBeFound("programDurationInWeeks.equals=" + DEFAULT_PROGRAM_DURATION_IN_WEEKS);

        // Get all the offerList where programDurationInWeeks equals to UPDATED_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldNotBeFound("programDurationInWeeks.equals=" + UPDATED_PROGRAM_DURATION_IN_WEEKS);
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks not equals to DEFAULT_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldNotBeFound("programDurationInWeeks.notEquals=" + DEFAULT_PROGRAM_DURATION_IN_WEEKS);

        // Get all the offerList where programDurationInWeeks not equals to UPDATED_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldBeFound("programDurationInWeeks.notEquals=" + UPDATED_PROGRAM_DURATION_IN_WEEKS);
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks in DEFAULT_PROGRAM_DURATION_IN_WEEKS or UPDATED_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldBeFound(
            "programDurationInWeeks.in=" + DEFAULT_PROGRAM_DURATION_IN_WEEKS + "," + UPDATED_PROGRAM_DURATION_IN_WEEKS
        );

        // Get all the offerList where programDurationInWeeks equals to UPDATED_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldNotBeFound("programDurationInWeeks.in=" + UPDATED_PROGRAM_DURATION_IN_WEEKS);
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks is not null
        defaultOfferShouldBeFound("programDurationInWeeks.specified=true");

        // Get all the offerList where programDurationInWeeks is null
        defaultOfferShouldNotBeFound("programDurationInWeeks.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks is greater than or equal to DEFAULT_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldBeFound("programDurationInWeeks.greaterThanOrEqual=" + DEFAULT_PROGRAM_DURATION_IN_WEEKS);

        // Get all the offerList where programDurationInWeeks is greater than or equal to UPDATED_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldNotBeFound("programDurationInWeeks.greaterThanOrEqual=" + UPDATED_PROGRAM_DURATION_IN_WEEKS);
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks is less than or equal to DEFAULT_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldBeFound("programDurationInWeeks.lessThanOrEqual=" + DEFAULT_PROGRAM_DURATION_IN_WEEKS);

        // Get all the offerList where programDurationInWeeks is less than or equal to SMALLER_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldNotBeFound("programDurationInWeeks.lessThanOrEqual=" + SMALLER_PROGRAM_DURATION_IN_WEEKS);
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsLessThanSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks is less than DEFAULT_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldNotBeFound("programDurationInWeeks.lessThan=" + DEFAULT_PROGRAM_DURATION_IN_WEEKS);

        // Get all the offerList where programDurationInWeeks is less than UPDATED_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldBeFound("programDurationInWeeks.lessThan=" + UPDATED_PROGRAM_DURATION_IN_WEEKS);
    }

    @Test
    @Transactional
    public void getAllOffersByProgramDurationInWeeksIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where programDurationInWeeks is greater than DEFAULT_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldNotBeFound("programDurationInWeeks.greaterThan=" + DEFAULT_PROGRAM_DURATION_IN_WEEKS);

        // Get all the offerList where programDurationInWeeks is greater than SMALLER_PROGRAM_DURATION_IN_WEEKS
        defaultOfferShouldBeFound("programDurationInWeeks.greaterThan=" + SMALLER_PROGRAM_DURATION_IN_WEEKS);
    }

    @Test
    @Transactional
    public void getAllOffersByRequiredSkillsIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where requiredSkills equals to DEFAULT_REQUIRED_SKILLS
        defaultOfferShouldBeFound("requiredSkills.equals=" + DEFAULT_REQUIRED_SKILLS);

        // Get all the offerList where requiredSkills equals to UPDATED_REQUIRED_SKILLS
        defaultOfferShouldNotBeFound("requiredSkills.equals=" + UPDATED_REQUIRED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllOffersByRequiredSkillsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where requiredSkills not equals to DEFAULT_REQUIRED_SKILLS
        defaultOfferShouldNotBeFound("requiredSkills.notEquals=" + DEFAULT_REQUIRED_SKILLS);

        // Get all the offerList where requiredSkills not equals to UPDATED_REQUIRED_SKILLS
        defaultOfferShouldBeFound("requiredSkills.notEquals=" + UPDATED_REQUIRED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllOffersByRequiredSkillsIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where requiredSkills in DEFAULT_REQUIRED_SKILLS or UPDATED_REQUIRED_SKILLS
        defaultOfferShouldBeFound("requiredSkills.in=" + DEFAULT_REQUIRED_SKILLS + "," + UPDATED_REQUIRED_SKILLS);

        // Get all the offerList where requiredSkills equals to UPDATED_REQUIRED_SKILLS
        defaultOfferShouldNotBeFound("requiredSkills.in=" + UPDATED_REQUIRED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllOffersByRequiredSkillsIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where requiredSkills is not null
        defaultOfferShouldBeFound("requiredSkills.specified=true");

        // Get all the offerList where requiredSkills is null
        defaultOfferShouldNotBeFound("requiredSkills.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByRequiredSkillsContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where requiredSkills contains DEFAULT_REQUIRED_SKILLS
        defaultOfferShouldBeFound("requiredSkills.contains=" + DEFAULT_REQUIRED_SKILLS);

        // Get all the offerList where requiredSkills contains UPDATED_REQUIRED_SKILLS
        defaultOfferShouldNotBeFound("requiredSkills.contains=" + UPDATED_REQUIRED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllOffersByRequiredSkillsNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where requiredSkills does not contain DEFAULT_REQUIRED_SKILLS
        defaultOfferShouldNotBeFound("requiredSkills.doesNotContain=" + DEFAULT_REQUIRED_SKILLS);

        // Get all the offerList where requiredSkills does not contain UPDATED_REQUIRED_SKILLS
        defaultOfferShouldBeFound("requiredSkills.doesNotContain=" + UPDATED_REQUIRED_SKILLS);
    }

    @Test
    @Transactional
    public void getAllOffersByTechnologiesIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where technologies equals to DEFAULT_TECHNOLOGIES
        defaultOfferShouldBeFound("technologies.equals=" + DEFAULT_TECHNOLOGIES);

        // Get all the offerList where technologies equals to UPDATED_TECHNOLOGIES
        defaultOfferShouldNotBeFound("technologies.equals=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllOffersByTechnologiesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where technologies not equals to DEFAULT_TECHNOLOGIES
        defaultOfferShouldNotBeFound("technologies.notEquals=" + DEFAULT_TECHNOLOGIES);

        // Get all the offerList where technologies not equals to UPDATED_TECHNOLOGIES
        defaultOfferShouldBeFound("technologies.notEquals=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllOffersByTechnologiesIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where technologies in DEFAULT_TECHNOLOGIES or UPDATED_TECHNOLOGIES
        defaultOfferShouldBeFound("technologies.in=" + DEFAULT_TECHNOLOGIES + "," + UPDATED_TECHNOLOGIES);

        // Get all the offerList where technologies equals to UPDATED_TECHNOLOGIES
        defaultOfferShouldNotBeFound("technologies.in=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllOffersByTechnologiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where technologies is not null
        defaultOfferShouldBeFound("technologies.specified=true");

        // Get all the offerList where technologies is null
        defaultOfferShouldNotBeFound("technologies.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByTechnologiesContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where technologies contains DEFAULT_TECHNOLOGIES
        defaultOfferShouldBeFound("technologies.contains=" + DEFAULT_TECHNOLOGIES);

        // Get all the offerList where technologies contains UPDATED_TECHNOLOGIES
        defaultOfferShouldNotBeFound("technologies.contains=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllOffersByTechnologiesNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where technologies does not contain DEFAULT_TECHNOLOGIES
        defaultOfferShouldNotBeFound("technologies.doesNotContain=" + DEFAULT_TECHNOLOGIES);

        // Get all the offerList where technologies does not contain UPDATED_TECHNOLOGIES
        defaultOfferShouldBeFound("technologies.doesNotContain=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllOffersByDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where details equals to DEFAULT_DETAILS
        defaultOfferShouldBeFound("details.equals=" + DEFAULT_DETAILS);

        // Get all the offerList where details equals to UPDATED_DETAILS
        defaultOfferShouldNotBeFound("details.equals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllOffersByDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where details not equals to DEFAULT_DETAILS
        defaultOfferShouldNotBeFound("details.notEquals=" + DEFAULT_DETAILS);

        // Get all the offerList where details not equals to UPDATED_DETAILS
        defaultOfferShouldBeFound("details.notEquals=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllOffersByDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where details in DEFAULT_DETAILS or UPDATED_DETAILS
        defaultOfferShouldBeFound("details.in=" + DEFAULT_DETAILS + "," + UPDATED_DETAILS);

        // Get all the offerList where details equals to UPDATED_DETAILS
        defaultOfferShouldNotBeFound("details.in=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllOffersByDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where details is not null
        defaultOfferShouldBeFound("details.specified=true");

        // Get all the offerList where details is null
        defaultOfferShouldNotBeFound("details.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByDetailsContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where details contains DEFAULT_DETAILS
        defaultOfferShouldBeFound("details.contains=" + DEFAULT_DETAILS);

        // Get all the offerList where details contains UPDATED_DETAILS
        defaultOfferShouldNotBeFound("details.contains=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllOffersByDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where details does not contain DEFAULT_DETAILS
        defaultOfferShouldNotBeFound("details.doesNotContain=" + DEFAULT_DETAILS);

        // Get all the offerList where details does not contain UPDATED_DETAILS
        defaultOfferShouldBeFound("details.doesNotContain=" + UPDATED_DETAILS);
    }

    @Test
    @Transactional
    public void getAllOffersByPaidIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where paid equals to DEFAULT_PAID
        defaultOfferShouldBeFound("paid.equals=" + DEFAULT_PAID);

        // Get all the offerList where paid equals to UPDATED_PAID
        defaultOfferShouldNotBeFound("paid.equals=" + UPDATED_PAID);
    }

    @Test
    @Transactional
    public void getAllOffersByPaidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where paid not equals to DEFAULT_PAID
        defaultOfferShouldNotBeFound("paid.notEquals=" + DEFAULT_PAID);

        // Get all the offerList where paid not equals to UPDATED_PAID
        defaultOfferShouldBeFound("paid.notEquals=" + UPDATED_PAID);
    }

    @Test
    @Transactional
    public void getAllOffersByPaidIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where paid in DEFAULT_PAID or UPDATED_PAID
        defaultOfferShouldBeFound("paid.in=" + DEFAULT_PAID + "," + UPDATED_PAID);

        // Get all the offerList where paid equals to UPDATED_PAID
        defaultOfferShouldNotBeFound("paid.in=" + UPDATED_PAID);
    }

    @Test
    @Transactional
    public void getAllOffersByPaidIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where paid is not null
        defaultOfferShouldBeFound("paid.specified=true");

        // Get all the offerList where paid is null
        defaultOfferShouldNotBeFound("paid.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByObservationsIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where observations equals to DEFAULT_OBSERVATIONS
        defaultOfferShouldBeFound("observations.equals=" + DEFAULT_OBSERVATIONS);

        // Get all the offerList where observations equals to UPDATED_OBSERVATIONS
        defaultOfferShouldNotBeFound("observations.equals=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllOffersByObservationsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where observations not equals to DEFAULT_OBSERVATIONS
        defaultOfferShouldNotBeFound("observations.notEquals=" + DEFAULT_OBSERVATIONS);

        // Get all the offerList where observations not equals to UPDATED_OBSERVATIONS
        defaultOfferShouldBeFound("observations.notEquals=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllOffersByObservationsIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where observations in DEFAULT_OBSERVATIONS or UPDATED_OBSERVATIONS
        defaultOfferShouldBeFound("observations.in=" + DEFAULT_OBSERVATIONS + "," + UPDATED_OBSERVATIONS);

        // Get all the offerList where observations equals to UPDATED_OBSERVATIONS
        defaultOfferShouldNotBeFound("observations.in=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllOffersByObservationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where observations is not null
        defaultOfferShouldBeFound("observations.specified=true");

        // Get all the offerList where observations is null
        defaultOfferShouldNotBeFound("observations.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByObservationsContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where observations contains DEFAULT_OBSERVATIONS
        defaultOfferShouldBeFound("observations.contains=" + DEFAULT_OBSERVATIONS);

        // Get all the offerList where observations contains UPDATED_OBSERVATIONS
        defaultOfferShouldNotBeFound("observations.contains=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllOffersByObservationsNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where observations does not contain DEFAULT_OBSERVATIONS
        defaultOfferShouldNotBeFound("observations.doesNotContain=" + DEFAULT_OBSERVATIONS);

        // Get all the offerList where observations does not contain UPDATED_OBSERVATIONS
        defaultOfferShouldBeFound("observations.doesNotContain=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllOffersByDomainIsEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where domain equals to DEFAULT_DOMAIN
        defaultOfferShouldBeFound("domain.equals=" + DEFAULT_DOMAIN);

        // Get all the offerList where domain equals to UPDATED_DOMAIN
        defaultOfferShouldNotBeFound("domain.equals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllOffersByDomainIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where domain not equals to DEFAULT_DOMAIN
        defaultOfferShouldNotBeFound("domain.notEquals=" + DEFAULT_DOMAIN);

        // Get all the offerList where domain not equals to UPDATED_DOMAIN
        defaultOfferShouldBeFound("domain.notEquals=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllOffersByDomainIsInShouldWork() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where domain in DEFAULT_DOMAIN or UPDATED_DOMAIN
        defaultOfferShouldBeFound("domain.in=" + DEFAULT_DOMAIN + "," + UPDATED_DOMAIN);

        // Get all the offerList where domain equals to UPDATED_DOMAIN
        defaultOfferShouldNotBeFound("domain.in=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllOffersByDomainIsNullOrNotNull() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where domain is not null
        defaultOfferShouldBeFound("domain.specified=true");

        // Get all the offerList where domain is null
        defaultOfferShouldNotBeFound("domain.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffersByDomainContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where domain contains DEFAULT_DOMAIN
        defaultOfferShouldBeFound("domain.contains=" + DEFAULT_DOMAIN);

        // Get all the offerList where domain contains UPDATED_DOMAIN
        defaultOfferShouldNotBeFound("domain.contains=" + UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void getAllOffersByDomainNotContainsSomething() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        // Get all the offerList where domain does not contain DEFAULT_DOMAIN
        defaultOfferShouldNotBeFound("domain.doesNotContain=" + DEFAULT_DOMAIN);

        // Get all the offerList where domain does not contain UPDATED_DOMAIN
        defaultOfferShouldBeFound("domain.doesNotContain=" + UPDATED_DOMAIN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOfferShouldBeFound(String filter) throws Exception {
        restOfferMockMvc
            .perform(get("/api/offers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offer.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionName").value(hasItem(DEFAULT_POSITION_NAME)))
            .andExpect(jsonPath("$.[*].programDurationInWeeks").value(hasItem(DEFAULT_PROGRAM_DURATION_IN_WEEKS)))
            .andExpect(jsonPath("$.[*].requiredSkills").value(hasItem(DEFAULT_REQUIRED_SKILLS)))
            .andExpect(jsonPath("$.[*].technologies").value(hasItem(DEFAULT_TECHNOLOGIES)))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS)))
            .andExpect(jsonPath("$.[*].paid").value(hasItem(DEFAULT_PAID.booleanValue())))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].domain").value(hasItem(DEFAULT_DOMAIN)));

        // Check, that the count call also returns 1
        restOfferMockMvc
            .perform(get("/api/offers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOfferShouldNotBeFound(String filter) throws Exception {
        restOfferMockMvc
            .perform(get("/api/offers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfferMockMvc
            .perform(get("/api/offers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOffer() throws Exception {
        // Get the offer
        restOfferMockMvc.perform(get("/api/offers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Update the offer
        Offer updatedOffer = offerRepository.findById(offer.getId()).get();
        // Disconnect from session so that the updates on updatedOffer are not directly saved in db
        em.detach(updatedOffer);
        updatedOffer
            .positionName(UPDATED_POSITION_NAME)
            .programDurationInWeeks(UPDATED_PROGRAM_DURATION_IN_WEEKS)
            .requiredSkills(UPDATED_REQUIRED_SKILLS)
            .technologies(UPDATED_TECHNOLOGIES)
            .details(UPDATED_DETAILS)
            .paid(UPDATED_PAID)
            .observations(UPDATED_OBSERVATIONS)
            .domain(UPDATED_DOMAIN);
        OfferDTO offerDTO = offerMapper.toDto(updatedOffer);

        restOfferMockMvc
            .perform(put("/api/offers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isOk());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
        Offer testOffer = offerList.get(offerList.size() - 1);
        assertThat(testOffer.getPositionName()).isEqualTo(UPDATED_POSITION_NAME);
        assertThat(testOffer.getProgramDurationInWeeks()).isEqualTo(UPDATED_PROGRAM_DURATION_IN_WEEKS);
        assertThat(testOffer.getRequiredSkills()).isEqualTo(UPDATED_REQUIRED_SKILLS);
        assertThat(testOffer.getTechnologies()).isEqualTo(UPDATED_TECHNOLOGIES);
        assertThat(testOffer.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testOffer.isPaid()).isEqualTo(UPDATED_PAID);
        assertThat(testOffer.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
        assertThat(testOffer.getDomain()).isEqualTo(UPDATED_DOMAIN);
    }

    @Test
    @Transactional
    public void updateNonExistingOffer() throws Exception {
        int databaseSizeBeforeUpdate = offerRepository.findAll().size();

        // Create the Offer
        OfferDTO offerDTO = offerMapper.toDto(offer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfferMockMvc
            .perform(put("/api/offers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(offerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offer in the database
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffer() throws Exception {
        // Initialize the database
        offerRepository.saveAndFlush(offer);

        int databaseSizeBeforeDelete = offerRepository.findAll().size();

        // Delete the offer
        restOfferMockMvc
            .perform(delete("/api/offers/{id}", offer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offer> offerList = offerRepository.findAll();
        assertThat(offerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
