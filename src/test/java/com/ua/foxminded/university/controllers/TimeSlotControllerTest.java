package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.dto.LectorDto;
import com.ua.foxminded.university.dto.LessonDto;
import com.ua.foxminded.university.dto.TimeSlotDto;
import com.ua.foxminded.university.model.*;
import com.ua.foxminded.university.services.TimeSlotServices;
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

import java.time.LocalDateTime;
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
class TimeSlotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeSlotServices service;

    @Test
    public void findAll_ShouldAddTimeSlotEntriesToModelAndRenderTimeSlotListView() throws Exception {
        Faculty firstFaculty = new Faculty(1, "it_department", null, null);
        Faculty secondFaculty = new Faculty(2, "IT-SCHOOL", null, null);

        LectorDto first = new LectorDto(1L, firstFaculty, "Bob", "Franlk");
        LectorDto second = new LectorDto(2L, secondFaculty, "Anri", "Kuscher");

        Group fb_12 = new Group("fb-12");
        Group fb_15 = new Group("fb-15");

        LessonDto java = new LessonDto(1L, "Java", first);
        LessonDto math = new LessonDto(2L, "Math", second);

        TimeSlotDto timeSlotDtoFirst = new TimeSlotDto(1L, LocalDateTime.of(2020, 11, 11, 11, 11, 11)
                , LocalDateTime.of(2020, 11, 11, 13, 11, 11), java, fb_12);

        TimeSlotDto timeSlotDtoSecond = new TimeSlotDto(2L, LocalDateTime.of(2020, 11, 11, 11, 11, 11),
                LocalDateTime.of(2020, 11, 11, 13, 11, 11), math, fb_15);

        when(service.getAll()).thenReturn(Arrays.asList(timeSlotDtoFirst, timeSlotDtoSecond));

        mockMvc.perform(get("/timeSlots"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeSlot/allTimeSlots"))
                .andExpect(model().attributeExists("timeSlots"))
                .andExpect(model().attribute("timeSlots", IsCollectionWithSize.hasSize(2)))
                .andExpect(model().attribute("timeSlots", hasItem(
                        allOf(
                                hasProperty("timeSlotId", is(1L)),
                                hasProperty("startLesson", is(LocalDateTime.of(2020, 11, 11, 11, 11, 11))),
                                hasProperty("endLesson", is(LocalDateTime.of(2020, 11, 11, 13, 11, 11))),
                                hasProperty("lessonDto", is(java)),
                                hasProperty("group", is(fb_12))
                        )
                )))
                .andExpect(model().attribute("timeSlots", hasItem(
                        allOf(
                                hasProperty("timeSlotId", is(2L)),
                                hasProperty("startLesson", is(LocalDateTime.of(2020, 11, 11, 11, 11, 11))),
                                hasProperty("endLesson", is(LocalDateTime.of(2020, 11, 11, 13, 11, 11))),
                                hasProperty("lessonDto", is(math)),
                                hasProperty("group", is(fb_15))
                        )
                )));


        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdShouldAddTimeSlotEntityToModelAndRenderTimeSlotForLessonView() throws Exception {

        Faculty firstFaculty = new Faculty(1, "it_department", null, null);
        LectorDto first = new LectorDto(1L, firstFaculty, "Bob", "Franlk");

        Group fb_12 = new Group("fb-12");

        LessonDto java = new LessonDto(1L, "Java", first);

        TimeSlotDto timeSlotDtoFirst = new TimeSlotDto(1L, LocalDateTime.of(2020, 11, 11, 11, 11, 11)
                , LocalDateTime.of(2020, 11, 11, 13, 11, 11), java, fb_12);

        when(service.getById(1L)).thenReturn(timeSlotDtoFirst);

        mockMvc.perform(get("/timeSlotProfileLesson/{timeSlotId}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/timeSlot/timeSlotProfileLesson"))
                .andExpect(model().attributeExists("timeSlot"))
                .andExpect(model().attribute("timeSlot", hasProperty("timeSlotId", is(1L))))
                .andExpect(model().attribute("timeSlot", hasProperty("startLesson", is(LocalDateTime.of(2020, 11, 11, 11, 11, 11)))))
                .andExpect(model().attribute("timeSlot", hasProperty("endLesson", is(LocalDateTime.of(2020, 11, 11, 13, 11, 11)))))
                .andExpect(model().attribute("timeSlot", hasProperty("lessonDto", is(java))))
                .andExpect(model().attribute("timeSlot", hasProperty("group", is(fb_12))));

        verify(service, times(1)).getById(1L);
        verifyNoMoreInteractions(service);
    }
    @Test
    public void getByIdShouldAddTimeSlotEntityToModelAndRenderTimeSlotForLectorView() throws Exception {

        Faculty firstFaculty = new Faculty(1, "it_department", null, null);
        LectorDto first = new LectorDto(1L, firstFaculty, "Bob", "Franlk");

        Group fb_12 = new Group("fb-12");

        LessonDto java = new LessonDto(1L, "Java", first);

        TimeSlotDto timeSlotDtoFirst = new TimeSlotDto(1L, LocalDateTime.of(2020, 11, 11, 11, 11, 11)
                , LocalDateTime.of(2020, 11, 11, 13, 11, 11), java, fb_12);

        when(service.getById(1L)).thenReturn(timeSlotDtoFirst);

        mockMvc.perform(get("/timeSlotProfileLector/{timeSlotId}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("/timeSlot/timeSlotProfileLector"))
                .andExpect(model().attributeExists("timeSlot"))
                .andExpect(model().attribute("timeSlot", hasProperty("timeSlotId", is(1L))))
                .andExpect(model().attribute("timeSlot", hasProperty("startLesson", is(LocalDateTime.of(2020, 11, 11, 11, 11, 11)))))
                .andExpect(model().attribute("timeSlot", hasProperty("endLesson", is(LocalDateTime.of(2020, 11, 11, 13, 11, 11)))))
                .andExpect(model().attribute("timeSlot", hasProperty("lessonDto", is(java))))
                .andExpect(model().attribute("timeSlot", hasProperty("group", is(fb_12))));

        verify(service, times(1)).getById(1L);
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getAllNotAddAnyLessonAndListIsEmpty() throws Exception {

        when(service.getAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/timeSlots"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeSlot/allTimeSlots"))
                .andExpect(model().attributeExists("timeSlots"))
                .andExpect(content().string(containsString("No Time slot available")))
                .andExpect(model().attribute("timeSlots",hasToString("[]")));

        verify(service, times(1)).getAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    public void getByIdNotAddAnyTimeSlotForLectorNotExistId() throws Exception {

        when(service.getById(2L)).thenReturn(null);

        mockMvc.perform(get("/timeSlotProfileLector/{timeSlotId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("/timeSlot/timeSlotProfileLector"))
                .andExpect(content().string(containsString("No such id")));


        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }
    @Test
    public void getByIdNotAddAnyTimeSlotForLessonNotExistId() throws Exception {

        when(service.getById(2L)).thenReturn(null);

        mockMvc.perform(get("/timeSlotProfileLesson/{timeSlotId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("/timeSlot/timeSlotProfileLesson"))
                .andExpect(content().string(containsString("No such id")));


        verify(service, times(1)).getById(2L);
        verifyNoMoreInteractions(service);
    }
}

