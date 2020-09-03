package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.dto.LectorDto;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.services.LectorServices;
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
class LectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectorServices service;


    @Test
    public void findAll_ShouldAddLectorEntriesToModelAndRenderLectorsListView() throws Exception {
        Faculty firstFaculty = new Faculty(1, "it_department", null, null);

        Faculty secondFaculty = new Faculty(2, "IT-SCHOOL", null, null);

        LectorDto first = new LectorDto(1L, firstFaculty, "Boris", "Novikov");

        LectorDto second = new LectorDto(2L, secondFaculty, "Andrey", "Karkaviy");

        when(service.getAll()).thenReturn(Arrays.asList(first, second));


        mockMvc.perform(get("/lectors"))
                .andExpect(status().isOk())
                .andExpect(view().name("lector/allLectors"))
                .andExpect(model().attributeExists("lectors"))
                .andExpect(model().attribute("lectors", IsCollectionWithSize.hasSize(2)))
                .andExpect(model().attribute("lectors", hasItem(
                        allOf(
                                hasProperty("lectorId", is(1L)),
                                hasProperty("firstName", is("Boris")),
                                hasProperty("lastName", is("Novikov")),
                                hasProperty("faculty", is(firstFaculty))
                        )
                )))
                .andExpect(model().attribute("lectors", hasItem(
                        allOf(
                                hasProperty("lectorId", is(2L)),
                                hasProperty("firstName", is("Andrey")),
                                hasProperty("lastName", is("Karkaviy")),
                                hasProperty("faculty", is(secondFaculty))
                        )
                )));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdShouldAddLectorEntityToModelAndRenderLectorView() throws Exception {
        Faculty firstFaculty = new Faculty(1, "it_department", null, null);

        LectorDto first = new LectorDto(1L, firstFaculty, "Boris", "Novikov");

        when(service.getById(1L)).thenReturn(first);

        mockMvc.perform(get("/lectorInfo/{lectorId}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("lector/lectorInfo"))
                .andExpect(model().attributeExists("lectorFaculty"))
                .andExpect(model().attribute("lectorFaculty", hasProperty("lectorId", is(1L))))
                .andExpect(model().attribute("lectorFaculty", hasProperty("firstName", is("Boris"))))
                .andExpect(model().attribute("lectorFaculty", hasProperty("lastName", is("Novikov"))))
                .andExpect(model().attribute("lectorFaculty", hasProperty("faculty", is(firstFaculty))));

        verify(service, times(1)).getById(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getAllNotAddAnyLectorWhenListIsEmpty() throws Exception {

        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/lectors"))
                .andExpect(status().isOk())
                .andExpect(view().name("lector/allLectors"))
                .andExpect(model().attributeExists("lectors"))
                .andExpect(content().string(containsString("No Lectors available")))
                .andExpect(model().attribute("lectors",hasToString("[]")));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdShouldNotAddAnyLectorWhenLectorIdIsNotExist() throws Exception {

        when(service.getById(2L)).thenReturn(null);

        mockMvc.perform(get("/lectorInfo/{lectorId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("lector/lectorInfo"))
                .andExpect(content().string(containsString("No such id")));


        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }

}

