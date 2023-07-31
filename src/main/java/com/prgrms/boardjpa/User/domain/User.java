package com.prgrms.boardjpa.User.domain;

import com.prgrms.boardjpa.global.ErrorCode;
import com.prgrms.boardjpa.global.domain.BaseEntity;
import com.prgrms.boardjpa.global.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

    private static final Pattern NAME_REGEX_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9]+$");
    private static final int MINIMUM_AGE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 40, unique = true)
    private String name;

    private int age;

    private String hobby;

    @Builder
    public User(String name, int age, String hobby) {
        validateAge(age);
        validateName(name);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)
                || !NAME_REGEX_PATTERN.matcher(name).matches()) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_NAME);
        }
    }

    private void validateAge(int age) {
        if (age < MINIMUM_AGE) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_AGE);
        }
    }
}