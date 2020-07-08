package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.dto.TimeSlotDto;
import com.ua.foxminded.task_13.dto.TimeSlotMapperDto;
import com.ua.foxminded.task_13.model.Group;
import com.ua.foxminded.task_13.model.Lesson;
import com.ua.foxminded.task_13.model.TimeSlot;
import com.ua.foxminded.task_13.services.TimeSlotServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/timeSlot")
public class TimeSlotController {

    private final TimeSlotServices timeSlotServices;

    @Autowired
    TimeSlotMapperDto mapperDto;

    @Autowired
    public TimeSlotController(TimeSlotServices timeSlotServices) {
        this.timeSlotServices = timeSlotServices;
    }

    @GetMapping("/timeSlots")
    public String allTimeSlots(Model model) {

        model.addAttribute("dto",timeSlotServices.getDTO(2L));

        model.addAttribute("timeSlots", timeSlotServices.getAll());
        return "timeSlot/allTimeSlots";
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public TimeSlotDto getTimeSlot(@PathVariable("faculty_id") Long id) {
        return timeSlotServices.getDTO(id);
    }

}

