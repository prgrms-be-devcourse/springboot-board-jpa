package com.example.board.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BindingException extends RuntimeException{
    private String bindingMessages;

    @Override
    public String getMessage() {
        return this.bindingMessages;
    }
}
