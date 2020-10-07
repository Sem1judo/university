package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.Lesson;
import com.ua.foxminded.university.model.mapper.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class LessonDaoImpl implements DaoEntity<Lesson> {

    private static final String GET_LESSON_BY_ID_QUERY = "select * from lessons " +
            "where lesson_id = ?";
    private static final String DELETE_LESSON_BY_ID_QUERY = "delete from lessons" +
            " where lesson_id = ?";
    private static final String UPDATE_LESSON_BY_ID_QUERY = "update lessons set lesson_name = ?,lector_id=? " +
            "where lesson_id = ?";
    private static final String SELECT_FROM_LESSONS_QUERY = "select * from lessons";
    private static final String INSERT_LESSON_QUERY = "insert into lessons(lesson_name,lector_id) " +
            "values(?,?)";

    @Autowired
    private  JdbcTemplate jdbcTemplate;

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
                lesson.getLessonId(), lesson.getLectorId()) > 0;
    }

    @Override
    public boolean create(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_LESSON_QUERY,
                    new String[]{"lesson_id"});
            ps.setString(1, lesson.getName());
            ps.setLong(2,lesson.getLectorId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue() > 0;
    }

}
