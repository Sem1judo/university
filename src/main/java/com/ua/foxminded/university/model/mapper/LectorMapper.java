package com.ua.foxminded.university.model.mapper;

import com.ua.foxminded.university.model.Lector;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LectorMapper implements RowMapper<Lector> {
    @Override
    public Lector mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Lector lector = new Lector();

        lector.setLectorId(resultSet.getLong("lector_id"));
        lector.setFirstName(resultSet.getString("first_name"));
        lector.setLastName(resultSet.getString("last_name"));
        lector.setFacultyId(resultSet.getLong("faculty_id"));

        return lector;
    }
}