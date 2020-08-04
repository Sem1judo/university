package com.ua.foxminded.university.dao.impl;


import com.ua.foxminded.university.model.Lesson;
import com.ua.foxminded.university.model.mapper.LessonMapper;
import com.ua.foxminded.university.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class LessonDaoImplTest {

    private final String CREATE_SQL = "insert into lessons(lesson_name) values(?)";
    private final String UPDATE_SQL = "update lessons set lesson_name = ? where lesson_id = ?";
    private final String DELETE_SQL = "delete from lessons where lesson_id = ?";
    private final String SELECT_SQL = "select * from lessons where lesson_id = ?";

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private LessonDaoImpl lessonDao;
    @InjectMocks
    ValidatorEntity validator;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(lessonDao, "jdbcTemplate", jdbcTemplate);

    }

    @Test
    public void shouldCreateLessonWhenAddValues() {
        when(jdbcTemplate.update(eq(CREATE_SQL), eq("testName")))
                .thenReturn(1);

        Lesson lesson = new Lesson();
        lesson.setName("testName");

        boolean isCreated = lessonDao.create(lesson);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedLessonWhenAddValuesWithId() {
        when(jdbcTemplate.update(eq(UPDATE_SQL),
                eq("testName"), eq(1L)))
                .thenReturn(1);

        Lesson lesson = new Lesson();
        lesson.setLessonId(1L);
        lesson.setName("testName");
        boolean isUpdated = lessonDao.update(lesson);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteLessonWhenInputId() {
        when(jdbcTemplate.update(eq(DELETE_SQL), eq(1L))).
                thenReturn(1);

        boolean isDeleted = lessonDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateNameWhenInputId() {
        when(jdbcTemplate.queryForObject(eq(SELECT_SQL), eq(new Object[]{100L}), (LessonMapper) any()))
                .thenReturn(getMeTestLesson());

        Lesson lesson = lessonDao.getById(100L);

        assertNotNull(lesson);
        assertEquals("testName", lesson.getName());
    }

    private Lesson getMeTestLesson() {
        Lesson lesson = new Lesson();
        lesson.setLessonId(100L);
        lesson.setName("testName");

        return lesson;
    }

}

