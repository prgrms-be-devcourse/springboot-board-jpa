package com.example.board.util;

import com.example.board.exception.BindingException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

public class Validation {
    public static void bindChecking(BindingResult bindingResult) {
        List<String> messages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                messages.add(objectError.getDefaultMessage());
            });
        }
        if (!messages.isEmpty()) {
            throw new BindingException(messages);
        }
    }
}
