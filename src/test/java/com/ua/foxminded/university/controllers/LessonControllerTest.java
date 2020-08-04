package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.dto.LectorDto;
import com.ua.foxminded.university.dto.LessonDto;
import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.services.LessonServices;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@ExtendWith(SpringExtension.class)
@WebAppConfiguration()
@AutoConfigureMockMvc
@ContextConfiguration(classes = {TestAppConfig.class})
class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LessonServices service;

    @Test
    public void findAll_ShouldAddLessonEntriesToModelAndRenderLessonsListView() throws Exception {
        Faculty firstFaculty = new Faculty(1, "it_department", null, null);

        Faculty secondFaculty = new Faculty(2, "IT-SCHOOL", null, null);

        LectorDto first = new LectorDto(1L, firstFaculty, "Bob", "Franlk");

        LectorDto second = new LectorDto(2L, secondFaculty, "Anri", "Kuscher");

        LessonDto java = new LessonDto(1L, "Java", first);
        LessonDto math = new LessonDto(2L, "Math", second);

        when(service.getAll()).thenReturn(Arrays.asList(java, math));

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("lesson/allLessons"))
                .andExpect(model().attributeExists("lessons"))
                .andExpect(model().attribute("lessons", IsCollectionWithSize.hasSize(2)))
                .andExpect(model().attribute("lessons", hasItem(
                        allOf(
                                hasProperty("lessonId", is(1L)),
                                hasProperty("name", is("Java")),
                                hasProperty("lector", is(first))
                        )
                )))
                .andExpect(model().attribute("lessons", hasItem(
                        allOf(
                                hasProperty("lessonId", is(2L)),
                                hasProperty("name", is("Math")),
                                hasProperty("lector", is(second))
                        )
                )));


        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdShouldAddLessonEntityToModelAndRenderLessonView() throws Exception {

        Faculty secondFaculty = new Faculty(2, "IT-SCHOOL", null, null);

        LectorDto second = new LectorDto(2L, secondFaculty, "Andrey", "Karkaviy");

        LessonDto java = new LessonDto(2L, "Math", second);

        when(service.getById(2L)).thenReturn(java);

        mockMvc.perform(get("/lessonProfileLector/{lessonId}", 2))
                .andExpect(status().isOk())
                .andExpect(view().name("lesson/lessonProfileLector"))
                .andExpect(model().attributeExists("lesson"))
                .andExpect(model().attribute("lesson", hasProperty("lessonId", is(2L))))
                .andExpect(model().attribute("lesson", hasProperty("name", is("Math"))))
                .andExpect(model().attribute("lesson", hasProperty("lector", is(second))));

        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getAllNotAddAnyLessonAndListIsEmpty() throws Exception {

        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/lessons"))
                .andExpect(status().isOk())
                .andExpect(view().name("lesson/allLessons"))
                .andExpect(model().attributeExists("lessons"))
                .andExpect(content().string(containsString("No Lessons available")))
                .andExpect(model().attribute("lessons",hasToString("[]")));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdNotAddAnyFacultyNotExistId() throws Exception {

        when(service.getById(2L)).thenReturn(null);

        mockMvc.perform(get("/lessonProfileLector/{lessonId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("lesson/lessonProfileLector"))
                .andExpect(content().string(containsString("No such id")));


        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }
}

