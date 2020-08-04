package com.ua.foxminded.task_13.dao.impl;

import com.ua.foxminded.task_13.dao.DaoEntity;
import com.ua.foxminded.task_13.model.Lesson;
import com.ua.foxminded.task_13.model.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import java.util.List;

@Repository
public class LessonDaoImpl implements DaoEntity<Lesson> {

    private static final String GET_LESSON_BY_ID_QUERY = "select * from lessons where lesson_id = ?";
    private static final String DELETE_LESSON_BY_ID_QUERY = "delete from lessons where lesson_id = ?";
    private static final String UPDATE_LESSON_BY_ID_QUERY = "update lessons set lesson_name = ?,lector_id=? where lesson_id = ?";
    private static final String SELECT_FROM_LESSONS_QUERY = "select * from lessons";
    private static final String INSERT_LESSON_QUERY = "insert into lessons(lesson_name,lector_id) values(?,?)";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LessonDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Lesson getById(Long id) {
        return jdbcTemplate.queryForObject(GET_LESSON_BY_ID_QUERY, new Object[]{id}, new LessonMapper());
    }

    @Override
    public List<Lesson> getAll() {
        return jdbcTemplate.query(SELECT_FROM_LESSONS_QUERY, new LessonMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_LESSON_BY_ID_QUERY, id) > 0;
    }

    @Override
    public boolean update(Lesson lesson) {
        return jdbcTemplate.update(UPDATE_LESSON_BY_ID_QUERY, lesson.getName(),
                lesson.getLessonId()) > 0;
    }

    @Override
    public boolean create(Lesson lesson) {
        return jdbcTemplate.update(INSERT_LESSON_QUERY, lesson.getName()) > 0;
    }

}
