package com.prgrms.springbootboardjpa.user.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@Embeddable
public class Password {
    @Column(nullable = false)
    private String password;

    //알파벳 대소문자 필수, 숫자 필수, 8자 이상
    private static final Pattern PATTERN = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");

    public Password(){}

    public Password(String password){
        verifyPassword(password);
        this.password = password;
    }

    private void verifyPassword(String password){
        if (!PATTERN.matcher(password).matches()){
            throw new IllegalArgumentException("비밀번호 옵션을 확인하세요 : 알파벳 대문자 필수, 소문자 필수, 숫자 필수, 8자 이상");
        }
    }
}
