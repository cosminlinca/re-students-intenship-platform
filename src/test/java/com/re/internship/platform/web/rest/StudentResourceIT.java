package com.re.internship.platform.web.rest;

import com.re.internship.platform.StudentsIntenshipPlatformAvraApp;
import com.re.internship.platform.domain.Student;
import com.re.internship.platform.repository.StudentRepository;
import com.re.internship.platform.service.StudentService;

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
 * Integration tests for the {@link StudentResource} REST controller.
 */
@SpringBootTest(classes = StudentsIntenshipPlatformAvraApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StudentResourceIT {

    private static final String DEFAULT_UNIVERSITY = "AAAAAAAAAA";
    private static final String UPDATED_UNIVERSITY = "BBBBBBBBBB";

    private static final String DEFAULT_FACULTY = "AAAAAAAAAA";
    private static final String UPDATED_FACULTY = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR_OF_STUDY = 1;
    private static final Integer UPDATED_YEAR_OF_STUDY = 2;

    private static final String DEFAULT_CV_PATH = "AAAAAAAAAA";
    private static final String UPDATED_CV_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVATIONS = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVATIONS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_CV_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CV_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CV_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CV_DOCUMENT_CONTENT_TYPE = "image/png";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStudentMockMvc;

    private Student student;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .university(DEFAULT_UNIVERSITY)
            .faculty(DEFAULT_FACULTY)
            .profile(DEFAULT_PROFILE)
            .yearOfStudy(DEFAULT_YEAR_OF_STUDY)
            .cvPath(DEFAULT_CV_PATH)
            .observations(DEFAULT_OBSERVATIONS)
            .cvDocument(DEFAULT_CV_DOCUMENT)
            .cvDocumentContentType(DEFAULT_CV_DOCUMENT_CONTENT_TYPE);
        return student;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createUpdatedEntity(EntityManager em) {
        Student student = new Student()
            .university(UPDATED_UNIVERSITY)
            .faculty(UPDATED_FACULTY)
            .profile(UPDATED_PROFILE)
            .yearOfStudy(UPDATED_YEAR_OF_STUDY)
            .cvPath(UPDATED_CV_PATH)
            .observations(UPDATED_OBSERVATIONS)
            .cvDocument(UPDATED_CV_DOCUMENT)
            .cvDocumentContentType(UPDATED_CV_DOCUMENT_CONTENT_TYPE);
        return student;
    }

    @BeforeEach
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();
        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getUniversity()).isEqualTo(DEFAULT_UNIVERSITY);
        assertThat(testStudent.getFaculty()).isEqualTo(DEFAULT_FACULTY);
        assertThat(testStudent.getProfile()).isEqualTo(DEFAULT_PROFILE);
        assertThat(testStudent.getYearOfStudy()).isEqualTo(DEFAULT_YEAR_OF_STUDY);
        assertThat(testStudent.getCvPath()).isEqualTo(DEFAULT_CV_PATH);
        assertThat(testStudent.getObservations()).isEqualTo(DEFAULT_OBSERVATIONS);
        assertThat(testStudent.getCvDocument()).isEqualTo(DEFAULT_CV_DOCUMENT);
        assertThat(testStudent.getCvDocumentContentType()).isEqualTo(DEFAULT_CV_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].university").value(hasItem(DEFAULT_UNIVERSITY)))
            .andExpect(jsonPath("$.[*].faculty").value(hasItem(DEFAULT_FACULTY)))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE)))
            .andExpect(jsonPath("$.[*].yearOfStudy").value(hasItem(DEFAULT_YEAR_OF_STUDY)))
            .andExpect(jsonPath("$.[*].cvPath").value(hasItem(DEFAULT_CV_PATH)))
            .andExpect(jsonPath("$.[*].observations").value(hasItem(DEFAULT_OBSERVATIONS)))
            .andExpect(jsonPath("$.[*].cvDocumentContentType").value(hasItem(DEFAULT_CV_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].cvDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_CV_DOCUMENT))));
    }
    
    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.university").value(DEFAULT_UNIVERSITY))
            .andExpect(jsonPath("$.faculty").value(DEFAULT_FACULTY))
            .andExpect(jsonPath("$.profile").value(DEFAULT_PROFILE))
            .andExpect(jsonPath("$.yearOfStudy").value(DEFAULT_YEAR_OF_STUDY))
            .andExpect(jsonPath("$.cvPath").value(DEFAULT_CV_PATH))
            .andExpect(jsonPath("$.observations").value(DEFAULT_OBSERVATIONS))
            .andExpect(jsonPath("$.cvDocumentContentType").value(DEFAULT_CV_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.cvDocument").value(Base64Utils.encodeToString(DEFAULT_CV_DOCUMENT)));
    }
    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findById(student.getId()).get();
        // Disconnect from session so that the updates on updatedStudent are not directly saved in db
        em.detach(updatedStudent);
        updatedStudent
            .university(UPDATED_UNIVERSITY)
            .faculty(UPDATED_FACULTY)
            .profile(UPDATED_PROFILE)
            .yearOfStudy(UPDATED_YEAR_OF_STUDY)
            .cvPath(UPDATED_CV_PATH)
            .observations(UPDATED_OBSERVATIONS)
            .cvDocument(UPDATED_CV_DOCUMENT)
            .cvDocumentContentType(UPDATED_CV_DOCUMENT_CONTENT_TYPE);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getUniversity()).isEqualTo(UPDATED_UNIVERSITY);
        assertThat(testStudent.getFaculty()).isEqualTo(UPDATED_FACULTY);
        assertThat(testStudent.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testStudent.getYearOfStudy()).isEqualTo(UPDATED_YEAR_OF_STUDY);
        assertThat(testStudent.getCvPath()).isEqualTo(UPDATED_CV_PATH);
        assertThat(testStudent.getObservations()).isEqualTo(UPDATED_OBSERVATIONS);
        assertThat(testStudent.getCvDocument()).isEqualTo(UPDATED_CV_DOCUMENT);
        assertThat(testStudent.getCvDocumentContentType()).isEqualTo(UPDATED_CV_DOCUMENT_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentMockMvc.perform(put("/api/students")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Delete the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
