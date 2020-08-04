package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.services.TimeSlotServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TimeSlotController {

    @Autowired
    private TimeSlotServices timeSlotServices;
    @Autowired
    private  MessageSource messageSource;

    @Autowired
    public TimeSlotController(TimeSlotServices timeSlotServices, MessageSource messageSource) {
        this.timeSlotServices = timeSlotServices;
        this.messageSource = messageSource;
    }

    @GetMapping("/timeSlots")
    public ModelAndView getAllTimeSlots(Model model) {
        ModelAndView mav = new ModelAndView("timeSlot/allTimeSlots");

        mav.addObject("timeSlots", timeSlotServices.getAll());

        return mav;
    }

    @GetMapping("/timeSlotProfileLesson/{timeSlotId}")
    public ModelAndView getLesson(@PathVariable("timeSlotId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("/timeSlot/timeSlotProfileLesson");

        mav.addObject("timeSlot", timeSlotServices.getById(id));

        return mav;
    }

    @GetMapping("/timeSlotProfileLector/{timeSlotId}")
    public ModelAndView getLector(@PathVariable("timeSlotId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("/timeSlot/timeSlotProfileLector");

        mav.addObject("timeSlot", timeSlotServices.getById(id));

        return mav;
    }

}

