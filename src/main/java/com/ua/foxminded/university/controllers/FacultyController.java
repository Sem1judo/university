package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.Faculty;
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

@Controller
public class FacultyController {

    @Autowired
    @Qualifier("facultyServices")
    private FacultyServices facultyServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/faculties")
    public ModelAndView getAllFaculties() {
        ModelAndView mav = new ModelAndView("faculty/allFaculties");

        mav.addObject("faculties", facultyServices.getAll());

        return mav;
    }

    @GetMapping("/facultyInfo/{facultyId}")
    public ModelAndView getFaculty(@PathVariable("facultyId") Long id) {
        ModelAndView mav = new ModelAndView("faculty/getFaculty");

        mav.addObject("faculty", facultyServices.getById(id));

        return mav;
    }

    @GetMapping("/createFacultyForm")
    public ModelAndView createFacultyForm(){
        ModelAndView mav = new ModelAndView("faculty/createFacultyForm");

        mav.addObject("faculty", new Faculty());

        return mav;
    }

    @PostMapping("/addFaculty")
    public ModelAndView addFaculty(@ModelAttribute Faculty faculty) {
        ModelAndView mav = new ModelAndView("faculty/addFaculty");

        facultyServices.create(faculty);

        faculty = facultyServices.getAll().get(facultyServices.getAll().size() - 1);

        mav.addObject("faculty", facultyServices.getById(faculty.getFacultyId()));

        return mav;
    }

    @GetMapping(value = "/deleteFaculty/{facultyId}")
    public ModelAndView deleteFaculty(@PathVariable("facultyId") long facultyId,Model model) {

        ModelAndView mav = new ModelAndView("faculty/allFaculties");

        facultyServices.delete(facultyId);

        mav.addObject("faculties", facultyServices.getAll());

        return mav;
    }

    @GetMapping("/edit/{facultyId}")
    ModelAndView showUpdateForm(@PathVariable("facultyId") Long facultyId, Model model) {

        ModelAndView mav = new ModelAndView("faculty/editFaculty");

        Faculty faculty = facultyServices.getById(facultyId);

        mav.addObject("faculty",faculty );

        return mav;
    }

    @PostMapping("/updateFaculty/{facultyId}")
    public ModelAndView updateFaculty(@PathVariable("facultyId") Long facultyId, Faculty faculty, Model model) {

        ModelAndView mav = new ModelAndView("faculty/deleteFaculty");

        facultyServices.update(faculty);

        mav.addObject("faculties", facultyServices.getAll());

        return mav;
    }
}
