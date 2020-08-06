package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.mapper.FacultyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FacultyDaoImpl implements DaoEntity<Faculty> {

    private static final String GET_FACULTY_BY_ID_QUERY = "select * from faculties " +
            "where faculty_id = ?";
    private static final String DELETE_FACULTY_BY_ID_QUERY = "delete from faculties " +
            "where faculty_id = ?";
    private static final String UPDATE_FACULTY_BY_ID_QUERY = "update faculties set faculty_name = ?" +
            "where faculty_id = ?";
    private static final String SELECT_FROM_FACULTIES_QUERY = "select * from faculties";
    private static final String INSERT_FACULTY_QUERY = "insert into faculties(faculty_name) " +
            "values(?)";

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Override
    public Faculty getById(Long id) {
        return jdbcTemplate.queryForObject(GET_FACULTY_BY_ID_QUERY, new Object[]{id}, new FacultyMapper());
    }

    @Override
    public List<Faculty> getAll() {
        return jdbcTemplate.query(SELECT_FROM_FACULTIES_QUERY, new FacultyMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_FACULTY_BY_ID_QUERY, id) > 0;
    }

    @Override
    public boolean update(Faculty faculty) {
        return jdbcTemplate.update(UPDATE_FACULTY_BY_ID_QUERY, faculty.getName(),
                faculty.getFacultyId()) > 0;
    }

    @Override
    public boolean create(Faculty faculty) {
        return jdbcTemplate.update(INSERT_FACULTY_QUERY, faculty.getName()) > 0;

    }

}
