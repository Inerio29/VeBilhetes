package com.vendabilhetes.model.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Página inicial redireciona para o login
    @GetMapping("/")
    public String index() {
        return "redirect:/login";
    }

    // Página de login (renderiza login.html)
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Página principal após login (renderiza home.html)
    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
