package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.FacultyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private final FacultyServices facultyServices;

    @Autowired
    public FacultyController(FacultyServices facultyServices) {
        this.facultyServices = facultyServices;
    }

    @GetMapping("/faculties")
    public String allFaculties(Model model) {
        model.addAttribute("faculties", facultyServices.getAll());
        return "faculty/allFaculties";
    }
}
