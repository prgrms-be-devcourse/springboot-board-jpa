package com.example.springbootboardjpa.exception;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@NoArgsConstructor
@Slf4j
public class NotFoundException extends SQLException {
    public NotFoundException(String s) {
        super(s);
        log.warn(s);
    }
}
