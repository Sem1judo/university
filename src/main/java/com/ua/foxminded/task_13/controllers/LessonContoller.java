package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lesson")
public class LessonContoller {

    private LessonServices lessonServices;

    @Autowired
    public LessonContoller(LessonServices lessonServices) {
        this.lessonServices = lessonServices;
    }

    @GetMapping("/lessons")
    public String allLessons(Model model) {
        model.addAttribute("lessons", lessonServices.getAll());
        return "lesson/allLessons";
    }

}
