package com.prgrms.springbootboardjpa.user.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@Embeddable
public class Name {
    @Column(nullable = false, length = 30)
    private String firstName;
    @Column(nullable = false, length = 30)
    private String lastName;

    private static final Pattern PATTERN = Pattern.compile("[^0-9\\.\\,\\\"\\?\\!\\;\\:\\#\\$\\%\\&\\(\\)\\*\\+\\-\\/\\<\\>\\=\\@\\[\\]\\\\\\^\\_\\{\\}\\|\\~]+");

    public Name(){}

    public Name(String firstName, String lastName) {
        verifyName(firstName);
        this.firstName = firstName;

        verifyName(lastName);
        this.lastName = lastName;
    }

    private void verifyName(String name){
        if (!PATTERN.matcher(name).matches()){
            throw new IllegalArgumentException("이름의 입력값이 잘못되었습니다.");
        }
    }
}