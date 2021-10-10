package com.example.boardbackend.controller;

import com.example.boardbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MvcTestController {
    private final UserService userService;

    @GetMapping("/")
    public String renderTestPage(Model model){
        var userList = userService.findUserAll();
        model.addAttribute("userList", userList);
        return "test";
    }
}
