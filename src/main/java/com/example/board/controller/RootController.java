package com.example.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {
    @GetMapping("/health")
    public String health() {
        return "I'm good";
    }
}
