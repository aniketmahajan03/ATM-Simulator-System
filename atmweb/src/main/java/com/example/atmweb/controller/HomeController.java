package com.example.atmweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    // @GetMapping("/")
    public String home() {
        return "✅ ATM Web Simulator is running successfully!";
    }

    @GetMapping("/test")
    public String test() {
        return "Hello, this is a test endpoint!";
    }
}
