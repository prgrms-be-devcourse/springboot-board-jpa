package com.study.board.domain.user.domain;

import com.study.board.domain.support.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkArgument;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    public static final int USER_NAME_MAX_LENGTH = 50;
    public static final int USER_HOBBY_MAX_LENGTH = 50;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "hobby", length = 50)
    private String hobby;

    public User(String name, String hobby) {
        checkArgument(hasText(name), "name - 글자를 가져야함");
        checkArgument(name.length() <= USER_NAME_MAX_LENGTH, "name 길이 - " + USER_NAME_MAX_LENGTH + " 이하 여야함");
        checkArgument(hobby == null || hobby.length() <= USER_HOBBY_MAX_LENGTH, "hobby 길이 - " + USER_HOBBY_MAX_LENGTH + " 이하 여야함");

        this.name = name;
        this.hobby = hobby;
    }
}
