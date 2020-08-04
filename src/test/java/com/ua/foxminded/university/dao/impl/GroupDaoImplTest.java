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

    private final String UPDATE_SQL = "update groups set group_name where group_id = ?";
    private final String CREATE_SQL = "insert into groups(group_name) values(?)";
    private final String DELETE_SQL = "delete from groups where group_id = ?";
    private final String SELECT_SQL = "select * from groups where group_id = ?";

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private GroupDaoImplDao groupDao;


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(groupDao, "jdbcTemplate", jdbcTemplate);
    }

    @Test
    public void shouldCreateGroupWhenAddValues() {
        when(jdbcTemplate.update(eq(CREATE_SQL),eq("testName")))
                .thenReturn(1);

        Group group = new Group();
        group.setName("testName");

        boolean isCreated = groupDao.create(group);
        assertTrue(isCreated);
    }

    @Test
    public void shouldUpdatedGroupWhenAddValuesWithId() {
        when(jdbcTemplate.update(eq(UPDATE_SQL), eq("testName"), eq(1L)))
                .thenReturn(1);

        Group group = new Group();
        group.setGroupId(1L);
        group.setName("testName");
        boolean isUpdated = groupDao.update(group);

        assertTrue(isUpdated);
    }

    @Test
    public void shouldDeleteGroupWhenInputId() {
        when(jdbcTemplate.update(eq(DELETE_SQL), eq(1L))).thenReturn(1);

        boolean isDeleted = groupDao.delete(1L);

        assertTrue(isDeleted);
    }

    @Test
    public void shouldReturnAppropriateNameWhenInputId() {
        when(jdbcTemplate.queryForObject(eq(SELECT_SQL),eq(new Object[]{100L})
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

