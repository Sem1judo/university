package com.ua.foxminded.task_13.model.mapper;

import com.ua.foxminded.task_13.model.Lector;
import com.ua.foxminded.task_13.model.TimeSlot;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TimeSLotMapper implements RowMapper<TimeSlot> {
    @Override
    public TimeSlot mapRow(ResultSet resultSet, int i) throws SQLException {
        TimeSlot timeSLot = new TimeSlot();

        timeSLot.setTimeSlotId(resultSet.getLong("timeslot_id"));
        timeSLot.setStartLesson(resultSet.getTimestamp("start_lesson").toLocalDateTime());
        timeSLot.setEndLesson(resultSet.getTimestamp("end_lesson").toLocalDateTime());
        timeSLot.setLessonId(resultSet.getLong("lesson_id"));
        timeSLot.setGroupId(resultSet.getLong("group_id"));

        return timeSLot;
    }
}
