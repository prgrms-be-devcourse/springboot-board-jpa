package com.example.boardbackend.controller;

import com.example.boardbackend.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcTestController {
    private final UserService userService;

    public MvcTestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String renderTestPage(Model model){
        var userList = userService.findUserAll();
        model.addAttribute("userList", userList);
        return "test";
    }
}
