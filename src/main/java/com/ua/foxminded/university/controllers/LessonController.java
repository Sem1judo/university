package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.services.LessonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LessonController {

    @Autowired
    @Qualifier("lessonServices")
    private LessonServices lessonServices;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/lessons")
    public ModelAndView getAllLessons(Model model) {
        ModelAndView mav = new ModelAndView("lesson/allLessons");

        mav.addObject("lessons", lessonServices.getAll());

        return mav;
    }

    @GetMapping("/lessonProfileLector/{lessonId}")
    public ModelAndView getTimeSlot(@PathVariable("lessonId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("lesson/lessonProfileLector");

        mav.addObject("lesson", lessonServices.getById(id));

        return mav;
    }


}
