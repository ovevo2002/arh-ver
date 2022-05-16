package ru.arh.athletics.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("login")
    public String registration(ModelMap modelMap) {
        modelMap.addAttribute("form", new LoginForm());

        return "login";
    }
}
