package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.FacultyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class FacultyController {

    private final FacultyServices facultyServices;

    private final MessageSource messageSource;

    @Autowired
    public FacultyController(MessageSource messageSource, FacultyServices facultyServices) {
        this.messageSource = messageSource;
        this.facultyServices = facultyServices;
    }

    @GetMapping("/faculties")
    public ModelAndView getAllFaculties(Model model) {
        ModelAndView mav = new ModelAndView("faculty/allFaculties");

        mav.addObject("faculties", facultyServices.getAll());

        return mav;
    }

    @GetMapping("/facultyInfo/{facultyId}")
    public ModelAndView getTimeSlot(@PathVariable("facultyId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("faculty/facultyInfo");

        mav.addObject("faculty", facultyServices.getById(id));

        return mav;
    }
}
