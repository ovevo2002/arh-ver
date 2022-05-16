package ru.arh.athletics.controllers;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginForm {

    @NotNull
    private String username;

    @NotNull
    @Min(6)
    private String password;
}
