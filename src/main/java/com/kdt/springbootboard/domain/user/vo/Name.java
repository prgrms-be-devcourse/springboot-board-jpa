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
public class Name {

    @Column(name = "user_name", nullable = false)
    private String name;

    public Name(String name) {
        if (!validate(name)) throw new IllegalArgumentException("Name should be korean.");
        this.name = name;
    }

    public static boolean validate(String name) {
        return Pattern.matches("^[가-힣]{2,5}$", name);
    }

    public String getName() {
        return name;
    }
}
