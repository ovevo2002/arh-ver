package ru.arh.athletics.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.arh.athletics.services.RegistrationService;

@Controller
@RequestMapping("registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public String registration(ModelMap modelMap) {
        modelMap.addAttribute("form", new LoginForm());

        return "registration";
    }

    @PostMapping
    public String registration(@ModelAttribute("form") @Validated LoginForm form, BindingResult errors) {
        if (!errors.hasErrors()) {
            registrationService.register(form.getUsername(), form.getPassword());
            return "redirect:/";
        }

        return "registration";
    }
}
