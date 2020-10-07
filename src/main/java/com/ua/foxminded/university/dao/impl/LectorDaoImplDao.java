package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.LectorDao;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.model.mapper.LectorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class LectorDaoImplDao implements LectorDao {

    private static final String GET_LECTOR_BY_ID_QUERY = "select * from lectors " +
            "where lector_id = ?";
    private static final String DELETE_LECTOR_BY_ID_QUERY = "delete from lectors " +
            "where lector_id = ?";
    private static final String UPDATE_LECTOR_BY_ID_QUERY = "update lectors set first_name = ?, last_name = ?,faculty_id=? " +
            "where lector_id = ?";
    private static final String SELECT_LECTORS_QUERY = "select * from lectors";
    private static final String INSERT_LECTOR_QUERY = "insert into lectors(first_name, last_name,faculty_id) " +
            "values(?,?,?)";
    private static final String GET_LESSONS_LECTORS_QUERY = "select count(lesson_id)*2 as quantity from time_slots\n" +
            "where time_slots.start_lesson between ? and ?";

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Override
    public Lector getById(Long id) {
        return jdbcTemplate.queryForObject(GET_LECTOR_BY_ID_QUERY, new Object[]{id}, new LectorMapper());
    }

    @Override
    public List<Lector> getAll() {
        return jdbcTemplate.query(SELECT_LECTORS_QUERY, new LectorMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_LECTOR_BY_ID_QUERY, id) > 0;
    }

    @Override
    public boolean update(Lector lector) {
        return jdbcTemplate.update(UPDATE_LECTOR_BY_ID_QUERY, lector.getFirstName(), lector.getLastName(),lector.getFacultyId(),
                lector.getLectorId()) > 0;
    }

    @Override
    public boolean create(Lector lector) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_LECTOR_QUERY,
                    new String[]{"lector_id"});
            ps.setString(1, lector.getFirstName());
            ps.setString(2,lector.getLastName());
            ps.setLong(3,lector.getFacultyId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue() > 0;
    }

    @Override
    public int getLessonsByTime(LocalDateTime start, LocalDateTime end) {
        return jdbcTemplate.queryForObject(GET_LESSONS_LECTORS_QUERY, new Object[]{start, end}, Integer.class);
    }
}
