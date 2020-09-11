package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.mapper.FacultyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class FacultyDaoImplTest {

    private final String UPDATE_SQL = "update faculties set faculty_name = ? where faculty_id = ?";
    private final String CREATE_SQL = "insert into faculties(faculty_name) values(?)";
    private final String DELETE_SQL = "delete from faculties where faculty_id = ?";
    private final String SELECT_SQL ="select * from faculties where faculty_id = ?";

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private FacultyDaoImpl facultyDao;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(facultyDao, "jdbcTemplate", jdbcTemplate);

    }

    @Test
    public void shouldCreateFacultyWhenAddValues() {
        when(jdbcTemplate.update(eq(CREATE_SQL),
                eq("testName")))
                .thenReturn(1);

        Faculty faculty = new Faculty();
        faculty.setFacultyId(1212L);
        faculty.setName("testName");

        boolean isCreated = facultyDao.create(faculty);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedFacultyWhenAddValuesWithId() {
        when(jdbcTemplate.update(eq(UPDATE_SQL), eq("testName"), eq(1L)))
                .thenReturn(1);

        Faculty faculty = new Faculty();
        faculty.setFacultyId(1L);
        faculty.setName("testName");
        boolean isUpdated = facultyDao.update(faculty);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteFacultyWhenInputId() {
        when(jdbcTemplate.update(eq(DELETE_SQL), eq(1L))).
                thenReturn(1);

        boolean isDeleted = facultyDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateNameWhenInputId() {
        when(jdbcTemplate.queryForObject(eq(SELECT_SQL), eq(new Object[]{100L})
                , (FacultyMapper) any())).thenReturn(getMeTestFaculty());

        Faculty faculty = facultyDao.getById(100L);

        assertNotNull(faculty);
        assertEquals("testName", faculty.getName());
    }

    private Faculty getMeTestFaculty() {
        Faculty faculty = new Faculty();
        faculty.setFacultyId(100L);
        faculty.setName("testName");

        return faculty;
    }

}


