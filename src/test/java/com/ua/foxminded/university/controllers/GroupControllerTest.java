package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.dto.GroupDto;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.services.GroupServices;
import com.ua.foxminded.university.testConfig.TestAppConfig;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestAppConfig.class})
class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupServices service;

    @Test
    public void findAll_ShouldAddGroupEntriesToModelAndRenderGroupsListView() throws Exception {
        Faculty itDepartment = new Faculty("it_department");
        Faculty itSchool = new Faculty("IT-SCHOOL");

        GroupDto fb_12 = new GroupDto();
        fb_12.setName("fb-12");
        fb_12.setGroupId(1L);
        fb_12.setFaculty(itDepartment);


        GroupDto fb_13 = new GroupDto();
        fb_13.setName("fb-13");
        fb_13.setGroupId(2L);
        fb_13.setFaculty(itSchool);


        when(service.getAll()).thenReturn(Arrays.asList(fb_12, fb_13));


        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/allGroups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(model().attribute("groups", IsCollectionWithSize.hasSize(2)))
                .andExpect(model().attribute("groups", hasItem(
                        allOf(
                                hasProperty("groupId", is(1L)),
                                hasProperty("name", is("fb-12")),
                                hasProperty("faculty", is(itDepartment))

                        )
                )))
                .andExpect(model().attribute("groups", hasItem(
                        allOf(
                                hasProperty("groupId", is(2L)),
                                hasProperty("name", is("fb-13")),
                                hasProperty("faculty", is(itSchool))
                        ))));


        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdShouldAddGroupEntityToModelAndRenderGroupView() throws Exception {
        Faculty itSchool = new Faculty("IT-SCHOOL");

        GroupDto fb_13 = new GroupDto();
        fb_13.setName("fb-13");
        fb_13.setGroupId(2L);
        fb_13.setFaculty(itSchool);

        when(service.getById(2L)).thenReturn(fb_13);

        mockMvc.perform(get("/groupInfo/{groupId}",2L))
                .andExpect(status().isOk())
                .andExpect(view().name("group/groupInfo"))
                .andExpect(model().attributeExists("groupFaculty"))
                .andExpect(model().attribute("groupFaculty", hasProperty("groupId", is(2L))))
                .andExpect(model().attribute("groupFaculty", hasProperty("name", is("fb-13"))))
                .andExpect(model().attribute("groupFaculty", hasProperty("faculty", is(itSchool))));


        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getAllNotAddAnyGroupAndListIsEmpty() throws Exception {

        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/allGroups"))
                .andExpect(model().attributeExists("groups"))
                .andExpect(content().string(containsString("No Groups available")))
                .andExpect(model().attribute("groups",hasToString("[]")));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdNotAddAnyGroupNotExistId() throws Exception {

        when(service.getById(2L)).thenReturn(null);

        mockMvc.perform(get("/groupInfo/{groupId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("group/groupInfo"))
                .andExpect(content().string(containsString("No such id")));


        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }
}

