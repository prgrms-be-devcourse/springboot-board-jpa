package com.spring.board.springboard.user.domain.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Component
public class EmailValidator {

    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public static void validate(String requestEmailInput){
        Assert.notNull(requestEmailInput, "이메일은 필수입니다.");

        if(!pattern.matcher(requestEmailInput)
                .matches()){
            throw new IllegalArgumentException("이메일 형식이 맞지 않습니다.");
        }
    }
}
