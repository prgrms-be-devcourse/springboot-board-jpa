package com.example.boardbackend.controller;

import com.example.boardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestDocsController {
    @GetMapping("/")
    public String renderRestDocs(){
        return "index";
    }
}
