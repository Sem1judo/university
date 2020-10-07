package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.dao.DaoEntity;
import com.ua.foxminded.university.model.Lesson;
import com.ua.foxminded.university.model.TimeSlot;
import com.ua.foxminded.university.model.mapper.TimeSLotMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Repository
public class TimeSlotDaoImpl implements DaoEntity<TimeSlot> {

    private static final String GET_TIME_SLOT_BY_ID_QUERY = "select * from time_slots " +
            "where timeslot_id = ?";
    private static final String DELETE_TIME_SLOT_BY_ID_QUERY = "delete from time_slots " +
            "where timeslot_id = ?";
    private static final String UPDATE_TIME_SLOT_BY_ID_QUERY = "update time_slots set start_lesson = ?, end_lesson = ?, group_id= ?, lesson_id= ? where timeslot_id = ?";
    private static final String SELECT_FROM_TIME_SLOTS_QUERY = "select * from time_slots";
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
        return jdbcTemplate.query(SELECT_FROM_TIME_SLOTS_QUERY, new TimeSLotMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_TIME_SLOT_BY_ID_QUERY, id) > 0;
    }

    @Override
    public boolean update(TimeSlot timeSlot) {
        return jdbcTemplate.update(UPDATE_TIME_SLOT_BY_ID_QUERY, timeSlot.getStartLesson(), timeSlot.getEndLesson(),timeSlot.getGroupId(),timeSlot.getLessonId(),
                timeSlot.getTimeSlotId()) > 0;
    }

    @Override
    public boolean create(TimeSlot timeSlot) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_TIME_SLOT_QUERY,
                    new String[]{"lesson_id"});
            ps.setObject(1, timeSlot.getStartLesson());
            ps.setObject(2,timeSlot.getEndLesson());
            ps.setLong(3,timeSlot.getGroupId());
            ps.setLong(4,timeSlot.getLessonId());
            return ps;
        }, keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue() > 0;
    }

}
