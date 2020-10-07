package com.ua.foxminded.university.controllers;

import com.ua.foxminded.university.model.Lector;
import com.ua.foxminded.university.model.Lesson;
import com.ua.foxminded.university.services.LessonServices;
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
    public ModelAndView getLesson(@PathVariable("lessonId") Long id, Model model) {
        ModelAndView mav = new ModelAndView("lesson/lessonProfileLector");

        mav.addObject("lesson", lessonServices.getById(id));

        return mav;
    }

    @GetMapping("/createLessonForm")
    public ModelAndView createLessonForm(Model model) {
        ModelAndView mav = new ModelAndView("lesson/createLessonForm");

        mav.addObject("lesson", new Lesson());

        return mav;
    }

    @PostMapping("/addLesson")
    public ModelAndView addLesson(@ModelAttribute Lesson lesson) {
        ModelAndView mav = new ModelAndView("lesson/addLesson");

        lessonServices.create(lesson);

        lesson = lessonServices.getAllLight().get(lessonServices.getAllLight().size() - 1);

        mav.addObject("lesson", lessonServices.getById(lesson.getLessonId()));

        return mav;
    }

}
