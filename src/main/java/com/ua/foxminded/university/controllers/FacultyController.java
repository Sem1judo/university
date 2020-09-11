package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.Faculty;
import com.ua.foxminded.university.model.Group;
import com.ua.foxminded.university.services.FacultyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.LinkedList;


@Controller
public class FacultyController {

    @Autowired
    @Qualifier("facultyServices")
    private FacultyServices facultyServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/faculties")
    public ModelAndView getAllFaculties(Model model) {
        ModelAndView mav = new ModelAndView("faculty/allFaculties");

        mav.addObject("faculties", facultyServices.getAll());

        return mav;
    }

    @GetMapping("/facultyInfo/{facultyId}")
    public ModelAndView getFaculty(@PathVariable("facultyId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("faculty/getFaculty");

        mav.addObject("faculty", facultyServices.getById(id));

        return mav;
    }

    @GetMapping("/createFacultyForm")
    public ModelAndView createFacultyForm(Model model) {
        ModelAndView mav = new ModelAndView("faculty/createFacultyForm");

        model.addAttribute("faculty", new Faculty());

        return mav;
    }

    @PostMapping("/addFaculty")
    public ModelAndView addFaculty(@ModelAttribute Faculty faculty) {
        ModelAndView mav = new ModelAndView("faculty/addFaculty");

        facultyServices.create(faculty);

        return mav;
    }
}
