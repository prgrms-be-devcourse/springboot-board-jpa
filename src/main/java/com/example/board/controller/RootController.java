package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class RootController {
    @GetMapping("/health")
    public String health() {
        return "I'm ok";
    }
}
