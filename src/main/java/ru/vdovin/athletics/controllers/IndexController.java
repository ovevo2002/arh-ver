package ru.arh.athletics.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private WorkoutController workoutController;

    @GetMapping("/")
    public String index() {
        return "forward:/workouts";
    }
}
