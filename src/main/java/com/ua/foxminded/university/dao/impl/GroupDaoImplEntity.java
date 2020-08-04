package com.ua.foxminded.university.dao.impl;


import com.ua.foxminded.university.dao.GroupEntity;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import java.util.List;

@Repository
public class GroupDaoImplEntity implements GroupEntity {

    private static final String GET_GROUP_BY_ID_QUERY = "select * from groups " +
            "where group_id = ?";
    private static final String DELETE_GROUP_BY_ID_QUERY = "delete from groups " +
            "where group_id = ?";
    private static final String UPDATE_GROUP_BY_ID_QUERY = "update groups set group_name =? ,faculty_id=? " +
            "where group_id = ?";
    private static final String SELECT_FROM_GROUPS_QUERY = "select * from groups";
    private static final String INSERT_GROUP_QUERY = "insert into groups(group_name,faculty_id) " +
            "values(?,?)";
    private static final String GET_LESSONS_FOR_GROUP_QUERY = "select lessons.lesson_name, count(time_slots.lesson_id)*2 as quantity from time_slots\n" +
            "join lessons on lessons.lesson_id = time_slots.lesson_id\n" +
            "join groups on groups.group_id = time_slots.group_id\n" +
            "where groups.group_id = ?\n" +
            "group by lessons.lesson_name";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDaoImplEntity(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Group getById(Long id) {
        return jdbcTemplate.queryForObject(GET_GROUP_BY_ID_QUERY, new Object[]{id}, new GroupMapper());
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SELECT_FROM_GROUPS_QUERY, new GroupMapper());
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update(DELETE_GROUP_BY_ID_QUERY, id) > 0;
    }

    @Override
    public boolean update(Group group) {
        return jdbcTemplate.update(UPDATE_GROUP_BY_ID_QUERY, group.getName(),
                group.getGroupId()) > 0;
    }

    @Override
    public boolean create(Group group) {
        return jdbcTemplate.update(INSERT_GROUP_QUERY, group.getName()) > 0;
    }

    @Override
    public int getLessonsById(Long id) {
        return jdbcTemplate.queryForObject(GET_LESSONS_FOR_GROUP_QUERY, new Object[]{id}, Integer.class);
    }
}




