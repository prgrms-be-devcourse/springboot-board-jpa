package com.study.board.domain.user.domain;

import com.study.board.domain.support.auditing.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static lombok.AccessLevel.PROTECTED;
import static org.springframework.util.StringUtils.hasText;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(name = "name", length = 50, nullable = false, unique = true)
    private String name;

    @Column(name = "hobby", length = 50)
    private String hobby;

    private User(Long id, String name, String hobby) {
        checkArgument(hasText(name), "name - 글자를 가져야함");
        checkArgument(name.length() <= 50, "name 길이 - 50 이하 여야함");
        if (hobby != null) {
            checkArgument(hobby.length() <= 50, "hobby 길이 - 50 이하 여야함");
        }

        this.id = id;
        this.name = name;
        this.hobby = hobby;
    }

    public static User create(String name, Optional<String> hobby) {
        return new User(null, name, hobby.orElse(null));
    }

    public Optional<String> getHobby() {
        return Optional.ofNullable(hobby);
    }

}
