package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.TimeSlotServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/timeSlot")
public class TimeSlotController {

    private final TimeSlotServices timeSlotServices;


    @Autowired
    public TimeSlotController(TimeSlotServices timeSlotServices) {
        this.timeSlotServices = timeSlotServices;
    }

    @GetMapping("/timeSlots")
    public String allTimeSlots(Model model) {

        model.addAttribute("dtos", timeSlotServices.getAllDTO());
        return "timeSlot/allTimeSlots";
    }

    @GetMapping("/profile/{timeSlotId}")
    public String getTimeSlot(@PathVariable("timeSlotId") Long id, Model model) {

        model.addAttribute("dto", timeSlotServices.getDTO(id));
        return "lector/profileForTimeSlot";
    }


}

