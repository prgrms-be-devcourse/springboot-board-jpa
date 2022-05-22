package com.prgrms.springbootboardjpa.user.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@Embeddable
public class Email {

    @Column(nullable = false, unique = true)
    private String email;

    private static final Pattern PATTERN = Pattern.compile("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b");

    public Email(){}

    public Email(String email){
        verifyEmail(email);
        this.email = email;
    }

    private void verifyEmail(String email){
        if (!PATTERN.matcher(email).matches()){
            throw new IllegalArgumentException("이메일의 입력값이 잘못되었습니다.");
        }
    }
}
