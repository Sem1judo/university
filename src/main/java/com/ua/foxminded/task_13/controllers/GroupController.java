package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.GroupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class GroupController {

    private final GroupServices groupServices;

    @Autowired
    public GroupController(GroupServices groupServices) {
        this.groupServices = groupServices;
    }

    @GetMapping("/groups")
    public String getAllGroups(Model model) {
        model.addAttribute("groups", groupServices.getAll());
        return "group/allGroups";
    }

    @GetMapping("/groupInfo/{groupId}")
    public String getTimeSlot(@PathVariable("groupId") Long id, Model model) {

        model.addAttribute("groupFaculty", groupServices.getById(id));
        return "group/groupInfo";
    }

}
