package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.model.mapper.LectorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class LectorDaoImplTest {

    private final String UPDATE_SQL = "update lectors set first_name = ?, last_name = ? where lector_id = ?";
    private final String CREATE_SQL = "insert into lectors(first_name, last_name) values(?,?)";
    private final String DELETE_SQL = "delete from lectors where lector_id = ?";
    private final String SELECT_SQL = "select * from lectors where lector_id = ?";

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private LectorDaoImplDao lectorDao;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(lectorDao, "jdbcTemplate", jdbcTemplate);

    }

    @Test
    public void shouldCreateLessonWhenAddValues() {
        when(jdbcTemplate.update(eq(CREATE_SQL), eq("testName"), eq("testSurname")))
                .thenReturn(1);

        Lector lector = new Lector();
        lector.setFirstName("testName");
        lector.setLastName("testSurname");

        boolean isCreated = lectorDao.create(lector);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedLectorWhenAddValuesWithId() {

        when(jdbcTemplate.update(eq(UPDATE_SQL),
                eq("testName"),
                eq("testSurname"), eq(1L)))
                .thenReturn(1);

        Lector lector = new Lector();
        lector.setFacultyId(1L);
        lector.setFirstName("testName");
        lector.setLastName("testSurname");
        lector.setLectorId(1L);
        boolean isUpdated = lectorDao.update(lector);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteLectorWhenInputId() {
        when(jdbcTemplate.update(eq(DELETE_SQL),
                eq(1L))).
                thenReturn(1);

        boolean isDeleted = lectorDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateFirstNameAndLastNameWhenInputId() {
        when(jdbcTemplate.queryForObject(eq(SELECT_SQL)
                , eq(new Object[]{100L})
                , (LectorMapper) any())).thenReturn(getMeTestLector());

        Lector lector = lectorDao.getById(100L);
        System.out.println(lector);

        assertNotNull(lector);
        assertEquals("testName", lector.getFirstName());
        assertEquals("testSurname", lector.getLastName());
    }


    private Lector getMeTestLector() {
        Lector lector = new Lector();
        lector.setLectorId(100L);
        lector.setFirstName("testName");
        lector.setLastName("testSurname");
        return lector;
    }

}

