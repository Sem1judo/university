package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.appConfig.AppConfig;
import com.ua.foxminded.task_13.dto.GroupDto;
import com.ua.foxminded.task_13.model.Faculty;
import com.ua.foxminded.task_13.model.Group;
import com.ua.foxminded.task_13.services.GroupServices;
import com.ua.foxminded.task_13.testConfig.TestAppConfig;
import com.ua.foxminded.task_13.validation.ValidatorEntity;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestAppConfig.class})
class GroupControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupServices groupServices;

    @Test
    public void getAll_ShouldAddGroupEntriesToModelAndRenderGroupListView() throws Exception {
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


        when(groupServices.getAll()).thenReturn(Arrays.asList(fb_12, fb_13));


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


        verify(groupServices, times(1)).getAll();
        verifyNoMoreInteractions(groupServices);
    }

    @Test
    public void getById_GroupEntryFound_ShouldAddGroupEntryToModelAndRenderViewGroupEntryView() throws Exception {
        Faculty itSchool = new Faculty("IT-SCHOOL");

        GroupDto fb_13 = new GroupDto();
        fb_13.setName("fb-13");
        fb_13.setGroupId(2L);
        fb_13.setFaculty(itSchool);

        when(groupServices.getById(2L)).thenReturn(fb_13);

        mockMvc.perform(get("/groupInfo/{groupId}",2L))
                .andExpect(status().isOk())
                .andExpect(view().name("group/groupInfo"))
                .andExpect(model().attributeExists("groupFaculty"))
                .andExpect(model().attribute("groupFaculty", hasProperty("groupId", is(2L))))
                .andExpect(model().attribute("groupFaculty", hasProperty("name", is("fb-13"))))
                .andExpect(model().attribute("groupFaculty", hasProperty("faculty", is(itSchool))));


        verify(groupServices, times(1)).getById(2L);
        verifyNoMoreInteractions(groupServices);
    }
}

