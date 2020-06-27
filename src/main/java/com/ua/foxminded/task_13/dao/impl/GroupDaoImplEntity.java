package com.ua.foxminded.task_13.dao.impl;


import com.ua.foxminded.task_13.dao.GroupEntity;
import com.ua.foxminded.task_13.model.Group;
import com.ua.foxminded.task_13.model.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

@Repository
public class GroupDaoImplEntity implements GroupEntity {

    private final JdbcTemplate jdbcTemplate;

    private static final String SQL_FIND_GROUPS = "select * from groups where group_id = ?";
    private static final String SQL_DELETE_GROUPS = "delete from groups where group_id = ?";
    private static final String SQL_UPDATE_GROUPS = "update groups set group_name where group_id = ?";
    private static final String SQL_GET_ALL_GROUPS = "select * from groups";
    private static final String SQL_INSERT_GROUPS = "insert into groups(group_name) values(?)";
    private static final String SQL_GET_LESSONS_GROUPS = "select lessons.lesson_name, count(time_slots.lesson_id)*2 as quantity from time_slots\n" +
            "join lessons on lessons.lesson_id = time_slots.lesson_id\n" +
            "join groups on groups.group_id = time_slots.group_id\n" +
            "where groups.group_id = ?\n" +
            "group by lessons.lesson_name";

    @Autowired
    public GroupDaoImplEntity(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group getById(Long id) {
        return jdbcTemplate.queryForObject(SQL_FIND_GROUPS, new Object[]{id}, new GroupMapper());
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SQL_GET_ALL_GROUPS, new GroupMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(SQL_DELETE_GROUPS, id) > 0;
    }

    @Override
    public boolean update(Group group) {
        return jdbcTemplate.update(SQL_UPDATE_GROUPS, group.getName(),
                group.getGroupId()) > 0;
    }

    @Override
    public boolean create(Group group) {
        return jdbcTemplate.update(SQL_INSERT_GROUPS, group.getName()) > 0;
    }

    @Override
    public int getLessonsById(Long id) {
        return jdbcTemplate.queryForObject(SQL_GET_LESSONS_GROUPS, new Object[]{id}, Integer.class);
    }
}




