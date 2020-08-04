package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.LectorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LectorController {

    private final LectorServices lectorServices;
    private final MessageSource messageSource;

    @Autowired
    public LectorController(LectorServices lectorServices, MessageSource messageSource) {
        this.lectorServices = lectorServices;
        this.messageSource = messageSource;
    }

    @GetMapping("/lectors")
    public ModelAndView getaAllLectors(Model model) {
        ModelAndView mav = new ModelAndView("lector/allLectors");

        mav.addObject("lectors", lectorServices.getAll());

        return mav;
    }

    @GetMapping("/lectorInfo/{lectorId}")
    public ModelAndView getTimeSlot(@PathVariable("lectorId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("lector/lectorInfo");

        mav.addObject("lectorFaculty", lectorServices.getById(id));

        return mav;
    }
}
