package com.ua.foxminded.task_13.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

//    @Autowired
//    private final FacultyServices facultyServices;

//    public FacultyController(FacultyServices facultyServices) {
//        this.facultyServices = facultyServices;
//    }

    @GetMapping("/faculties")
    public String getAllFaculties(Model model) {
//        model.addAttribute("faculties", facultyServices.getAll());
        return "faculty/faculties";
    }
}
