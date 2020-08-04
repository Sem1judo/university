package com.ua.foxminded.task_13.controllers;


import com.ua.foxminded.task_13.dto.GroupDto;
import com.ua.foxminded.task_13.model.Faculty;
import com.ua.foxminded.task_13.model.Group;
import com.ua.foxminded.task_13.model.Lector;
import com.ua.foxminded.task_13.services.FacultyServices;
import com.ua.foxminded.task_13.testConfig.TestAppConfig;
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


import java.util.*;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestAppConfig.class})
class FacultyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    FacultyServices service;

    @Test
    public void findAll_ShouldAddFacultyEntriesToModelAndRenderFacultyListView() throws Exception {

        Group fb1 = new Group("Fb1");
        Group fb2 = new Group("Fb2");

        Lector lector1 = new Lector("Abe", "Iata");
        Lector lector2 = new Lector("Marok", "Hifume");
        List<Lector> lectors = Arrays.asList(lector1, lector2);
        List<Group> groups = Arrays.asList(fb1, fb2);
        Faculty first = new Faculty(1L, "it_department", groups, lectors);

        Faculty second = new Faculty(2L, "IT-SCHOOL", groups, lectors);

        when(service.getAll()).thenReturn(Arrays.asList(first, second));

        mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculty/allFaculties"))
                .andExpect(model().attributeExists("faculties"))
                .andExpect(model().attribute("faculties", IsCollectionWithSize.hasSize(2)))
                .andExpect(model().attribute("faculties", hasItem(
                        allOf(
                                hasProperty("facultyId", is(1L)),
                                hasProperty("name", is("it_department")),
                                hasProperty("groups", is(groups)),
                                hasProperty("lectors", is(lectors))
                        )
                )))
                .andExpect(model().attribute("faculties", hasItem(
                        allOf(
                                hasProperty("facultyId", is(2L)),
                                hasProperty("name", is("IT-SCHOOL")),
                                hasProperty("groups", is(groups)),
                                hasProperty("lectors", is(lectors))
                        )
                )));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdShouldAddFacultyEntityToModelAndRenderFacultyView() throws Exception {
        Faculty itSchool = new Faculty("IT-SCHOOL");
        itSchool.setFacultyId(2L);

        when(service.getById(2L)).thenReturn(itSchool);

        mockMvc.perform(get("/facultyInfo/{facultyId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("faculty/facultyInfo"))
                .andExpect(model().attributeExists("faculty"))
                .andExpect(model().attribute("faculty", hasProperty("facultyId", is(2L))))
                .andExpect(model().attribute("faculty", hasProperty("name", is("IT-SCHOOL"))));

        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getAllNotAddAnyFacultyAndListIsEmpty() throws Exception {

        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/faculties"))
                .andExpect(status().isOk())
                .andExpect(view().name("faculty/allFaculties"))
                .andExpect(model().attributeExists("faculties"))
                .andExpect(content().string(containsString("No Faculties Available")))
                .andExpect(model().attribute("faculties",hasToString("[]")));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdNotAddAnyFacultyNotExistId() throws Exception {

        when(service.getById(2L)).thenReturn(null);

        mockMvc.perform(get("/facultyInfo/{facultyId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("faculty/facultyInfo"))
                .andExpect(content().string(containsString("No such id")));


        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }

}

