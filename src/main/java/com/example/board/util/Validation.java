package com.example.board.util;

import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class Validation {
    public static List<String> bindChecking(BindingResult bindingResult) {
        List<String> messages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                messages.add(objectError.getDefaultMessage());
            });
        }
        return messages;
    }
}
