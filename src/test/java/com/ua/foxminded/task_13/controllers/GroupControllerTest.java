package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.appConfig.AppConfig;
import com.ua.foxminded.task_13.dto.GroupDto;
import com.ua.foxminded.task_13.model.Faculty;
import com.ua.foxminded.task_13.model.Group;
import com.ua.foxminded.task_13.services.GroupServices;
import com.ua.foxminded.task_13.validation.ValidatorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Arrays;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContext.class, AppConfig.class})
@WebAppConfiguration
class GroupControllerTest {


    private MockMvc mockMvc;

    @Autowired
    private GroupServices groupServices;

    @Autowired
    private AppConfig appConfig;

    @BeforeEach
    void init() {
        Mockito.reset(groupServices);
        mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) appConfig).build();

    }

    @Test
    public void getAll_ShouldAddGroupEntriesToModelAndRenderGroupListView() throws Exception {
        GroupDto fb_12 = new GroupDto();
        fb_12.setName("fb-12");
        fb_12.setGroupId(1);
        fb_12.setFaculty(new Faculty("it_department"));

        GroupDto fb_13 = new GroupDto();
        fb_13.setName("fb-13");
        fb_13.setGroupId(2);
        fb_13.setFaculty(new Faculty("IT-SCHOOL"));

        GroupDto fb_14 = new GroupDto();
        fb_14.setName("fb-14");
        fb_14.setGroupId(3);
        fb_14.setFaculty(new Faculty("new1 department"));

        GroupDto fb_15 = new GroupDto();
        fb_15.setName("fb-15");
        fb_15.setGroupId(4);
        fb_15.setFaculty(new Faculty("new2 department"));

        GroupDto fb_16 = new GroupDto();
        fb_16.setName("fb-16");
        fb_16.setGroupId(5);
        fb_16.setFaculty(new Faculty("new3 department"));


        when(groupServices.getAll()).thenReturn(Arrays.asList(fb_12, fb_13,fb_14,fb_15,fb_16));

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(view().name("group/allGroups"))
                .andExpect(forwardedUrl("/WEB-INF/views/group/allGroups.html"))
                .andExpect(model().attribute("groups", hasSize(5)))
                .andExpect(model().attribute("groups", hasItem(
                        allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("name", is("fb-12")),
                                hasProperty("faculty.getName()", is("it_department"))
                        )
                )))
                .andExpect(model().attribute("todos", hasItem(
                        allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("name", is("fb-13")),
                                hasProperty("faculty.getName()", is("IT-SCHOOL"))
                        )
                )));

        verify(groupServices, times(1)).getAll();
        verifyNoMoreInteractions(groupServices);
    }
    @Test
    public void getById_GroupEntryFound_ShouldAddGroupEntryToModelAndRenderViewGroupEntryView() throws Exception {
        GroupDto fb_13 = new GroupDto();
        fb_13.setName("fb-13");
        fb_13.setGroupId(2);
        fb_13.setFaculty(new Faculty("IT-SCHOOL"));

        when(groupServices.getById(2L)).thenReturn(fb_13);

        mockMvc.perform(get("/groupInfo/{groupId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("todo/view"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/todo/view.jsp"))
                .andExpect(model().attribute("groupFaculty", hasProperty("Faculty.getName()", is("IT-SCHOOL"))));

        verify(groupServices, times(1)).getById(2L);
        verifyNoMoreInteractions(groupServices);
    }
}