package com.ua.foxminded.task_13.controllers;

import com.ua.foxminded.task_13.services.LectorServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lector")
public class LectorController {

    private final LectorServices lectorServices;

    @Autowired
    public LectorController(LectorServices lectorServices) {
        this.lectorServices = lectorServices;
    }

    @GetMapping("/lectors")
    public String allLectors(Model model) {
        model.addAttribute("lectors", lectorServices.getAll());
        return "lector/allLectors";
    }
}
