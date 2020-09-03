package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.services.GroupServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class GroupController {

    @Autowired
    @Qualifier("groupServices")
    private  GroupServices groupServices;
    @Autowired
    private  MessageSource messageSource;

    @GetMapping("/groups")
    public ModelAndView getAllGroups(Model model) {
        ModelAndView mav = new ModelAndView("group/allGroups");

        mav.addObject("groups", groupServices.getAll());

        return mav;
    }

    @GetMapping("/groupInfo/{groupId}")
    public ModelAndView getTimeSlot(@PathVariable("groupId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("group/groupInfo");

        mav.addObject("groupFaculty", groupServices.getById(id));

        return mav;
    }

}
