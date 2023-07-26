package com.prgrms.boardjpa.User.domain;

import com.prgrms.boardjpa.Post.domain.Post;
import com.prgrms.boardjpa.global.ErrorCode;
import com.prgrms.boardjpa.global.domain.BaseEntity;
import com.prgrms.boardjpa.global.exception.InvalidDomainConditionException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    private static final int MINIMUM_AGE = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, length = 40, unique = true)
    private String name;

    private int age;

    private String hobby;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, int age, String hobby, List<Post> posts) {
        validateAge(age);
        validateName(name);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.posts = posts;
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_NAME);
        }
    }

    private void validateAge(int age) {
        if (age < MINIMUM_AGE) {
            throw new InvalidDomainConditionException(ErrorCode.INVALID_AGE);
        }
    }
}