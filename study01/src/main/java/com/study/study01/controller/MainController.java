package com.study.study01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "main/main";
    }

    @GetMapping("/error")
    public String error() {
        return "/error/error";
    }
}
