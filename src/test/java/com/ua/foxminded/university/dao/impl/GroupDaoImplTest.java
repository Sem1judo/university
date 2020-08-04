package com.ua.foxminded.university.dao.impl;

import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.model.mapper.GroupMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class GroupDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private GroupDaoImplEntity groupDao;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(groupDao, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void shouldCreateGroupWhenAddValues() {
        when(jdbcTemplate.update(eq("insert into groups(group_name) values(?)"),eq("testName")))
                .thenReturn(1);

        Group group = new Group();
        group.setName("testName");

        boolean isCreated = groupDao.create(group);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedGroupWhenAddValuesWithId() {
        when(jdbcTemplate.update(eq("update groups set group_name where group_id = ?"), eq("testName"), eq(1L)))
                .thenReturn(1);

        Group group = new Group();
        group.setGroupId(1L);
        group.setName("testName");
        boolean isUpdated = groupDao.update(group);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteGroupWhenInputId() {
        when(jdbcTemplate.update(eq("delete from groups where group_id = ?"), eq(1L))).thenReturn(1);

        boolean isDeleted = groupDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateNameWhenInputId() {
        when(jdbcTemplate.queryForObject(eq("select * from groups where group_id = ?"),eq(new Object[]{100L})
                ,(GroupMapper) any())).thenReturn(getMeTestGroup());

        Group group = groupDao.getById(100L);

        assertNotNull(group);
        assertEquals("testName", group.getName());
    }

    private Group getMeTestGroup() {
        Group group = new Group();
        group.setGroupId(100L);
        group.setName("testName");

        return group;
    }
}

