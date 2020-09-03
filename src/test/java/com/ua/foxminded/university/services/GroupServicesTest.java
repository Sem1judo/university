package com.ua.foxminded.university.services;

import com.ua.foxminded.university.dao.impl.GroupDaoImplDao;
import com.ua.foxminded.university.exceptions.ServiceException;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GroupServicesTest {
    @Mock
    private GroupDaoImplDao groupDao;

    @InjectMocks
    private ValidatorEntity<Group> validator;

    @InjectMocks
    private GroupServices groupServices;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(groupServices, "validator", validator);

    }

    @Test
    public void getAllShouldGetAllGroups() {
        List<Group> initialGroups = new ArrayList<>();
        Group testGroup1 = new Group(1, 1, "Fb-12");
        Group testGroup2 = new Group(2, 2, "IT-22");
        Group testGroup3 = new Group(3, 3, "NA-11");

        initialGroups.add(testGroup1);
        initialGroups.add(testGroup2);
        initialGroups.add(testGroup3);

        when(groupDao.getAll()).thenReturn(initialGroups);

        List<Group> groups = groupServices.getAllLight();

        assertEquals(3, groups.size());
        verify(groupDao, times(1)).getAll();
    }

    @Test
    public void getByIdShouldGetByIdGroup() {
        when(groupDao.getById(1L)).thenReturn(new Group(1, 1, "Fb-12"));

        Group group = groupServices.getByIdLight(1L);

        assertEquals("Fb-12", group.getName());
        assertEquals(1, group.getGroupId());
    }

    @Test
    public void createShouldCreateGroup() {
        Group group = new Group(1, 1, "Fb-12");

        when(groupDao.create(eq(group))).thenReturn(Boolean.TRUE);

        boolean isCreated = groupServices.create(group);

        verify(groupDao, times(1)).create(group);
        assertTrue(isCreated);
    }

    @Test
    public void deleteShouldDeleteGroup() {
        when(groupDao.delete(eq(1L))).thenReturn(Boolean.TRUE);

        boolean isDeleted = groupServices.delete(1L);

        assertTrue(isDeleted);
        verify(groupDao, times(1)).delete(1L);
    }

    @Test
    public void updateShouldUpdateGroup() {
        Group group = new Group(1, 1, "Fb-12");

        when(groupDao.update(eq(group))).thenReturn(Boolean.TRUE);

        boolean isCreated = groupServices.update(group);

        verify(groupDao, times(1)).update(group);
        assertTrue(isCreated);
    }

    @Test
    public void createShouldThrowServiceExceptionWhenNameIsNull() {
        Group group = new Group(1, 1, null);
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

    @Test
    public void createShouldThrowServiceExceptionWhenNameTooShort() {
        Group group = new Group(1, 1, "Fb");
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

    @Test
    public void updateShouldThrowServiceExceptionWhenIdZero() {
        Group group = new Group(0, 1, "Fb-12");
        assertThrows(ServiceException.class, () -> groupServices.update(group));
    }

    @Test
    public void createShouldThrowServiceExceptionWhenNameHaveForbiddenSymbol() {
        Group group = new Group(1, 1, "Fb_12");
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

    @Test
    public void createShouldThrowServiceExceptionWhenNameIsTooLong() {
        Group group = new Group(1, 1, "aaaaaaaaaaFbaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        assertThrows(ServiceException.class, () -> groupServices.create(group));
    }

}

