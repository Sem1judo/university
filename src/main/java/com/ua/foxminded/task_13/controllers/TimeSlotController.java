package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.TimeSlotServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TimeSlotController {

    private final TimeSlotServices timeSlotServices;


    @Autowired
    public TimeSlotController(TimeSlotServices timeSlotServices) {
        this.timeSlotServices = timeSlotServices;
    }

    @GetMapping("/timeSlots")
    public String getAllTimeSlots(Model model) {

        model.addAttribute("timeslots", timeSlotServices.getAll());
        return "timeSlot/allTimeSlots";
    }

    @GetMapping("/timeSlotProfileLesson/{timeSlotId}")
    public String getLesson(@PathVariable("timeSlotId") Long id, Model model) {

        model.addAttribute("timeSlot", timeSlotServices.getById(id));
        return "/timeSlot/timeSlotProfileLesson";
    }

    @GetMapping("/timeSlotProfileLector/{timeSlotId}")
    public String getLector(@PathVariable("timeSlotId") Long id, Model model) {
        model.addAttribute("timeSlot", timeSlotServices.getById(id));
        return "/timeSlot/timeSlotProfileLector";
    }

}

