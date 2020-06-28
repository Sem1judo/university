package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.FacultyServices;
import com.ua.foxminded.task_13.services.GroupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/group")
public class GroupController {

    private final GroupServices groupServices;

    @Autowired
    public GroupController(GroupServices groupServices) {
        this.groupServices = groupServices;
    }

    @GetMapping("/groups")
    public String allFaculties(Model model) {
        model.addAttribute("groups", groupServices.getAll());
        return "group/allGroups";
    }

}
