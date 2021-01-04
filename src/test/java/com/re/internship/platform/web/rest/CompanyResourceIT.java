package com.re.internship.platform.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.re.internship.platform.StudentsIntenshipPlatformAvraApp;
import com.re.internship.platform.domain.Company;
import com.re.internship.platform.repository.CompanyRepository;
import com.re.internship.platform.service.CompanyQueryService;
import com.re.internship.platform.service.CompanyService;
import com.re.internship.platform.service.dto.CompanyCriteria;
import com.re.internship.platform.service.dto.CompanyDTO;
import com.re.internship.platform.service.mapper.CompanyMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = StudentsIntenshipPlatformAvraApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {
    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DOMAIN_OF_ACTIVITY = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_OF_ACTIVITY = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGIES = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGIES = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PRESENTATION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PRESENTATION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PRESENTATION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PRESENTATION_CONTENT_TYPE = "image/png";

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyQueryService companyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .domainOfActivity(DEFAULT_DOMAIN_OF_ACTIVITY)
            .technologies(DEFAULT_TECHNOLOGIES)
            .contact(DEFAULT_CONTACT)
            .address(DEFAULT_ADDRESS)
            .observations(DEFAULT_OBSERVATIONS)
            .presentation(DEFAULT_PRESENTATION)
            .presentationContentType(DEFAULT_PRESENTATION_CONTENT_TYPE);
        return company;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .domainOfActivity(UPDATED_DOMAIN_OF_ACTIVITY)
            .technologies(UPDATED_TECHNOLOGIES)
            .contact(UPDATED_CONTACT)
            .address(UPDATED_ADDRESS)
            .observations(UPDATED_OBSERVATIONS)
            .presentation(UPDATED_PRESENTATION)
            .presentationContentType(UPDATED_PRESENTATION_CONTENT_TYPE);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);
        restCompanyMockMvc
            .perform(post("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompany.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCompany.getDomainOfActivity()).isEqualTo(DEFAULT_DOMAIN_OF_ACTIVITY);
        assertThat(testCompany.getTechnologies()).isEqualTo(DEFAULT_TECHNOLOGIES);
        assertThat(testCompany.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testCompany.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCompany.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
        assertThat(testCompany.getPresentation()).isEqualTo(DEFAULT_PRESENTATION);
        assertThat(testCompany.getPresentationContentType()).isEqualTo(DEFAULT_PRESENTATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].domainOfActivity").value(hasItem(DEFAULT_DOMAIN_OF_ACTIVITY)))
            .andExpect(jsonPath("$.[*].technologies").value(hasItem(DEFAULT_TECHNOLOGIES)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].presentationContentType").value(hasItem(DEFAULT_PRESENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRESENTATION))));
    }

    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.domainOfActivity").value(DEFAULT_DOMAIN_OF_ACTIVITY))
            .andExpect(jsonPath("$.technologies").value(DEFAULT_TECHNOLOGIES))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS))
            .andExpect(jsonPath("$.presentationContentType").value(DEFAULT_PRESENTATION_CONTENT_TYPE))
            .andExpect(jsonPath("$.presentation").value(Base64Utils.encodeToString(DEFAULT_PRESENTATION)));
    }

    @Test
    @Transactional
    public void getCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        Long id = company.getId();

        defaultCompanyShouldBeFound("id.equals=" + id);
        defaultCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCompanyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name equals to DEFAULT_NAME
        defaultCompanyShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name not equals to DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the companyList where name not equals to UPDATED_NAME
        defaultCompanyShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCompanyShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the companyList where name equals to UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name is not null
        defaultCompanyShouldBeFound("name.specified=true");

        // Get all the companyList where name is null
        defaultCompanyShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name contains DEFAULT_NAME
        defaultCompanyShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the companyList where name contains UPDATED_NAME
        defaultCompanyShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where name does not contain DEFAULT_NAME
        defaultCompanyShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the companyList where name does not contain UPDATED_NAME
        defaultCompanyShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description equals to DEFAULT_DESCRIPTION
        defaultCompanyShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description equals to UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description not equals to DEFAULT_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description not equals to UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the companyList where description equals to UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description is not null
        defaultCompanyShouldBeFound("description.specified=true");

        // Get all the companyList where description is null
        defaultCompanyShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description contains DEFAULT_DESCRIPTION
        defaultCompanyShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description contains UPDATED_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where description does not contain DEFAULT_DESCRIPTION
        defaultCompanyShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the companyList where description does not contain UPDATED_DESCRIPTION
        defaultCompanyShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDomainOfActivityIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where domainOfActivity equals to DEFAULT_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldBeFound("domainOfActivity.equals=" + DEFAULT_DOMAIN_OF_ACTIVITY);

        // Get all the companyList where domainOfActivity equals to UPDATED_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldNotBeFound("domainOfActivity.equals=" + UPDATED_DOMAIN_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDomainOfActivityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where domainOfActivity not equals to DEFAULT_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldNotBeFound("domainOfActivity.notEquals=" + DEFAULT_DOMAIN_OF_ACTIVITY);

        // Get all the companyList where domainOfActivity not equals to UPDATED_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldBeFound("domainOfActivity.notEquals=" + UPDATED_DOMAIN_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDomainOfActivityIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where domainOfActivity in DEFAULT_DOMAIN_OF_ACTIVITY or UPDATED_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldBeFound("domainOfActivity.in=" + DEFAULT_DOMAIN_OF_ACTIVITY + "," + UPDATED_DOMAIN_OF_ACTIVITY);

        // Get all the companyList where domainOfActivity equals to UPDATED_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldNotBeFound("domainOfActivity.in=" + UPDATED_DOMAIN_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDomainOfActivityIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where domainOfActivity is not null
        defaultCompanyShouldBeFound("domainOfActivity.specified=true");

        // Get all the companyList where domainOfActivity is null
        defaultCompanyShouldNotBeFound("domainOfActivity.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByDomainOfActivityContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where domainOfActivity contains DEFAULT_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldBeFound("domainOfActivity.contains=" + DEFAULT_DOMAIN_OF_ACTIVITY);

        // Get all the companyList where domainOfActivity contains UPDATED_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldNotBeFound("domainOfActivity.contains=" + UPDATED_DOMAIN_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByDomainOfActivityNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where domainOfActivity does not contain DEFAULT_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldNotBeFound("domainOfActivity.doesNotContain=" + DEFAULT_DOMAIN_OF_ACTIVITY);

        // Get all the companyList where domainOfActivity does not contain UPDATED_DOMAIN_OF_ACTIVITY
        defaultCompanyShouldBeFound("domainOfActivity.doesNotContain=" + UPDATED_DOMAIN_OF_ACTIVITY);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTechnologiesIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where technologies equals to DEFAULT_TECHNOLOGIES
        defaultCompanyShouldBeFound("technologies.equals=" + DEFAULT_TECHNOLOGIES);

        // Get all the companyList where technologies equals to UPDATED_TECHNOLOGIES
        defaultCompanyShouldNotBeFound("technologies.equals=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTechnologiesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where technologies not equals to DEFAULT_TECHNOLOGIES
        defaultCompanyShouldNotBeFound("technologies.notEquals=" + DEFAULT_TECHNOLOGIES);

        // Get all the companyList where technologies not equals to UPDATED_TECHNOLOGIES
        defaultCompanyShouldBeFound("technologies.notEquals=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTechnologiesIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where technologies in DEFAULT_TECHNOLOGIES or UPDATED_TECHNOLOGIES
        defaultCompanyShouldBeFound("technologies.in=" + DEFAULT_TECHNOLOGIES + "," + UPDATED_TECHNOLOGIES);

        // Get all the companyList where technologies equals to UPDATED_TECHNOLOGIES
        defaultCompanyShouldNotBeFound("technologies.in=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTechnologiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where technologies is not null
        defaultCompanyShouldBeFound("technologies.specified=true");

        // Get all the companyList where technologies is null
        defaultCompanyShouldNotBeFound("technologies.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByTechnologiesContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where technologies contains DEFAULT_TECHNOLOGIES
        defaultCompanyShouldBeFound("technologies.contains=" + DEFAULT_TECHNOLOGIES);

        // Get all the companyList where technologies contains UPDATED_TECHNOLOGIES
        defaultCompanyShouldNotBeFound("technologies.contains=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllCompaniesByTechnologiesNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where technologies does not contain DEFAULT_TECHNOLOGIES
        defaultCompanyShouldNotBeFound("technologies.doesNotContain=" + DEFAULT_TECHNOLOGIES);

        // Get all the companyList where technologies does not contain UPDATED_TECHNOLOGIES
        defaultCompanyShouldBeFound("technologies.doesNotContain=" + UPDATED_TECHNOLOGIES);
    }

    @Test
    @Transactional
    public void getAllCompaniesByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contact equals to DEFAULT_CONTACT
        defaultCompanyShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the companyList where contact equals to UPDATED_CONTACT
        defaultCompanyShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contact not equals to DEFAULT_CONTACT
        defaultCompanyShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the companyList where contact not equals to UPDATED_CONTACT
        defaultCompanyShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByContactIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultCompanyShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the companyList where contact equals to UPDATED_CONTACT
        defaultCompanyShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contact is not null
        defaultCompanyShouldBeFound("contact.specified=true");

        // Get all the companyList where contact is null
        defaultCompanyShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByContactContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contact contains DEFAULT_CONTACT
        defaultCompanyShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the companyList where contact contains UPDATED_CONTACT
        defaultCompanyShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByContactNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where contact does not contain DEFAULT_CONTACT
        defaultCompanyShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the companyList where contact does not contain UPDATED_CONTACT
        defaultCompanyShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address equals to DEFAULT_ADDRESS
        defaultCompanyShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the companyList where address equals to UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address not equals to DEFAULT_ADDRESS
        defaultCompanyShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the companyList where address not equals to UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the companyList where address equals to UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address is not null
        defaultCompanyShouldBeFound("address.specified=true");

        // Get all the companyList where address is null
        defaultCompanyShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address contains DEFAULT_ADDRESS
        defaultCompanyShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the companyList where address contains UPDATED_ADDRESS
        defaultCompanyShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where address does not contain DEFAULT_ADDRESS
        defaultCompanyShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the companyList where address does not contain UPDATED_ADDRESS
        defaultCompanyShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByObservationsIsEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where observations equals to DEFAULT_OBSERVATIONS
        defaultCompanyShouldBeFound("observations.equals=" + DEFAULT_OBSERVATIONS);

        // Get all the companyList where observations equals to UPDATED_OBSERVATIONS
        defaultCompanyShouldNotBeFound("observations.equals=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByObservationsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where observations not equals to DEFAULT_OBSERVATIONS
        defaultCompanyShouldNotBeFound("observations.notEquals=" + DEFAULT_OBSERVATIONS);

        // Get all the companyList where observations not equals to UPDATED_OBSERVATIONS
        defaultCompanyShouldBeFound("observations.notEquals=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByObservationsIsInShouldWork() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where observations in DEFAULT_OBSERVATIONS or UPDATED_OBSERVATIONS
        defaultCompanyShouldBeFound("observations.in=" + DEFAULT_OBSERVATIONS + "," + UPDATED_OBSERVATIONS);

        // Get all the companyList where observations equals to UPDATED_OBSERVATIONS
        defaultCompanyShouldNotBeFound("observations.in=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByObservationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where observations is not null
        defaultCompanyShouldBeFound("observations.specified=true");

        // Get all the companyList where observations is null
        defaultCompanyShouldNotBeFound("observations.specified=false");
    }

    @Test
    @Transactional
    public void getAllCompaniesByObservationsContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where observations contains DEFAULT_OBSERVATIONS
        defaultCompanyShouldBeFound("observations.contains=" + DEFAULT_OBSERVATIONS);

        // Get all the companyList where observations contains UPDATED_OBSERVATIONS
        defaultCompanyShouldNotBeFound("observations.contains=" + UPDATED_OBSERVATIONS);
    }

    @Test
    @Transactional
    public void getAllCompaniesByObservationsNotContainsSomething() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList where observations does not contain DEFAULT_OBSERVATIONS
        defaultCompanyShouldNotBeFound("observations.doesNotContain=" + DEFAULT_OBSERVATIONS);

        // Get all the companyList where observations does not contain UPDATED_OBSERVATIONS
        defaultCompanyShouldBeFound("observations.doesNotContain=" + UPDATED_OBSERVATIONS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCompanyShouldBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].domainOfActivity").value(hasItem(DEFAULT_DOMAIN_OF_ACTIVITY)))
            .andExpect(jsonPath("$.[*].technologies").value(hasItem(DEFAULT_TECHNOLOGIES)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].presentationContentType").value(hasItem(DEFAULT_PRESENTATION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].presentation").value(hasItem(Base64Utils.encodeToString(DEFAULT_PRESENTATION))));

        // Check, that the count call also returns 1
        restCompanyMockMvc
            .perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCompanyShouldNotBeFound(String filter) throws Exception {
        restCompanyMockMvc
            .perform(get("/api/companies?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCompanyMockMvc
            .perform(get("/api/companies/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .domainOfActivity(UPDATED_DOMAIN_OF_ACTIVITY)
            .technologies(UPDATED_TECHNOLOGIES)
            .contact(UPDATED_CONTACT)
            .address(UPDATED_ADDRESS)
            .observations(UPDATED_OBSERVATIONS)
            .presentation(UPDATED_PRESENTATION)
            .presentationContentType(UPDATED_PRESENTATION_CONTENT_TYPE);
        CompanyDTO companyDTO = companyMapper.toDto(updatedCompany);

        restCompanyMockMvc
            .perform(put("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompany.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCompany.getDomainOfActivity()).isEqualTo(UPDATED_DOMAIN_OF_ACTIVITY);
        assertThat(testCompany.getTechnologies()).isEqualTo(UPDATED_TECHNOLOGIES);
        assertThat(testCompany.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCompany.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
        assertThat(testCompany.getPresentation()).isEqualTo(UPDATED_PRESENTATION);
        assertThat(testCompany.getPresentationContentType()).isEqualTo(UPDATED_PRESENTATION_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Create the Company
        CompanyDTO companyDTO = companyMapper.toDto(company);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc
            .perform(delete("/api/companies/{id}", company.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
