package com.ua.foxminded.task_13.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/faculty")
public class FacultyController {

    @GetMapping("/faculties")
    public String allFaculties() {
        return "faculty/allFaculties";
    }
}
