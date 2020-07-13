package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class LessonController {

    private final LessonServices lessonServices;

    @Autowired
    public LessonController(LessonServices lessonServices) {
        this.lessonServices = lessonServices;
    }

    @GetMapping("/lessons")
    public String getAllLessons(Model model) {
        model.addAttribute("lessons", lessonServices.getAll());
        return "lesson/allLessons";
    }

    @GetMapping("/lessonProfileLector/{lessonId}")
    public String getTimeSlot(@PathVariable("lessonId") Long id, Model model) {

        model.addAttribute("lesson", lessonServices.getById(id));
        return "lesson/lessonProfileLector";
    }


}
