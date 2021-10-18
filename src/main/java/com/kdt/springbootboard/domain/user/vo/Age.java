package com.kdt.springbootboard.domain.user.vo;

import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Age {

    @Column(name = "user_age", nullable = false)
    private String age;

    public Age(String age) {
        if (!validate(age)) throw new IllegalArgumentException("Invalid Age number");
        this.age = age;
    }

    public static boolean validate(String age) {
        return Pattern.matches("^100|[1-9]?\\d$", age);
    }

    public String getAge() {
        return String.valueOf(age);
    }

}
