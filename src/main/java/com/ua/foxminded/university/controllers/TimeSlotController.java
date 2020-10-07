package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.TimeSlot;
import com.ua.foxminded.university.services.TimeSlotServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
public class TimeSlotController {

    @Autowired
    @Qualifier("timeSlotServices")
    private TimeSlotServices timeSlotServices;
    @Autowired
    private  MessageSource messageSource;

    @Autowired
    public TimeSlotController(TimeSlotServices timeSlotServices, MessageSource messageSource) {
        this.timeSlotServices = timeSlotServices;
        this.messageSource = messageSource;
    }

    @GetMapping("/timeSlots")
    public ModelAndView getAllTimeSlots() {
        ModelAndView mav = new ModelAndView("timeSlot/allTimeSlots");

        mav.addObject("timeSlots", timeSlotServices.getAll());

        return mav;
    }

    @GetMapping("/timeSlotProfileLesson/{timeSlotId}")
    public ModelAndView getLesson(@PathVariable("timeSlotId") Long id) {
        ModelAndView mav = new ModelAndView("/timeSlot/timeSlotProfileLesson");

        mav.addObject("timeSlot", timeSlotServices.getById(id));

        return mav;
    }

    @GetMapping("/timeSlotProfileLector/{timeSlotId}")
    public ModelAndView getLector(@PathVariable("timeSlotId") Long id) {
        ModelAndView mav = new ModelAndView("/timeSlot/timeSlotProfileLector");

        mav.addObject("timeSlot", timeSlotServices.getById(id));

        return mav;
    }

    @GetMapping("/createTimeSlotForm")
    public ModelAndView createTimeSlotForm() {
        ModelAndView mav = new ModelAndView("timeSlot/createTimeSlotForm");

        mav.addObject("timeSlot", new TimeSlot(LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(2),1,1 ));

        return mav;
    }

    @PostMapping("/addTimeSlot")
    public ModelAndView addTimeSlot(@ModelAttribute TimeSlot timeSlot){
        ModelAndView mav = new ModelAndView("timeSlot/addTimeSLot");

        timeSlotServices.create(timeSlot);

        timeSlot = timeSlotServices.getAllLight().get(timeSlotServices.getAllLight().size() - 1);

        mav.addObject("timeSlot",timeSlotServices.getById(timeSlot.getTimeSlotId()));

        return mav;
    }

}

