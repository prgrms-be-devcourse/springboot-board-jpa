package com.example.board.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

@Component
public class MessageUtil {

    @Resource
    private MessageSource source;

    static MessageSource messageSource;

    @PostConstruct
    private void init() {
        messageSource = source;
    }

    public static String getMessage(FieldError fieldError) {
        return messageSource.getMessage(fieldError, Locale.KOREA);
    }

    public static String getMessage(String message) {
        return messageSource.getMessage(message, null, "오류 메시지를 찾지 못했습니다.", Locale.KOREA);
    }
}
