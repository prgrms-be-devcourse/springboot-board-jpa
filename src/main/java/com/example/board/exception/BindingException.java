package com.example.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class BindingException extends RuntimeException {
    private final List<String> bindingMessages;

    @Override
    public String getMessage() {
        String errorMessages = "";
        for (String message : bindingMessages) {
            errorMessages += (message + System.lineSeparator());
        }
        return errorMessages;
    }
}
