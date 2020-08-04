package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.TimeSlot;
import com.ua.foxminded.university.model.mapper.TimeSLotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

@Repository
public class TimeSlotDaoImpl implements DaoEntity<TimeSlot> {

    private static final String GET_TIME_SLOT_BY_ID_QUERY = "select * from time_slots " +
            "where timeslot_id = ?";
    private static final String DELETE_TIME_SLOT_BY_ID_QUERY = "delete from time_slots " +
            "where timeslot_id = ?";
    private static final String UPDATE_TIME_SLOT_BY_ID_QUERY = "update time_slots set start_lesson = ?, end_lesson = ?, group_id= ?, lesson_id= ? where timeslot_id = ?";
    private static final String SELECT_FROM_TIME_SLOTS_QUERT = "select * from time_slots";
    private static final String INSERT_TIME_SLOT_QUERY = "insert into time_slots(start_lesson, end_lesson, group_id, lesson_id) " +
            "values(?,?,?,?)";

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Override
    public TimeSlot getById(Long id) {
        return jdbcTemplate.queryForObject(GET_TIME_SLOT_BY_ID_QUERY, new Object[]{id}, new TimeSLotMapper());
    }

    @Override
    public List<TimeSlot> getAll() {
        return jdbcTemplate.query(SELECT_FROM_TIME_SLOTS_QUERT, new TimeSLotMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_TIME_SLOT_BY_ID_QUERY, id) > 0;
    }

    @Override
    public boolean update(TimeSlot timeSlot) {
        return jdbcTemplate.update(UPDATE_TIME_SLOT_BY_ID_QUERY, timeSlot.getStartLesson(), timeSlot.getEndLesson(),
                timeSlot.getTimeSlotId()) > 0;
    }

    @Override
    public boolean create(TimeSlot timeSlot) {
        return jdbcTemplate.update(INSERT_TIME_SLOT_QUERY, timeSlot.getStartLesson(), timeSlot.getEndLesson()) > 0;
    }

}
