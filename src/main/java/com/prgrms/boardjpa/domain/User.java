package com.prgrms.boardjpa.domain;

import com.prgrms.boardjpa.exception.LengthErrorException;
import com.prgrms.boardjpa.exception.WrongFormatException;
import com.prgrms.boardjpa.user.dto.AuthorDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
@Getter
public class User extends BaseEntity {

    public static final int MAX_NAME_LENGTH = 30;
    public static final int MAX_HOBBY_LENGTH = 30;
    public static final String NAME_REGEX = "[a-zA-Z가-힣]+( [a-zA-Z가-힣]+)*";
    public static final String HOBBY_REGEX = "(( )*([\\w가-힣\\.\\(\\)\\[\\]\\-\"'?/*&^%$#@!~=+,])*)*";

    private static void validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new LengthErrorException("유저의 이름은 %d자를 넘을 수 없습니다.".formatted(MAX_NAME_LENGTH));
        }
        if (!Pattern.matches(NAME_REGEX, name)) {
            throw new WrongFormatException("올바르지 않은 유저의 이름입니다.");
        }
    }

    private static void validateAge(int age) {
        if (age < 0) {
            throw new WrongFormatException("올바르지 않은 유저의 나이입니다.");
        }
    }

    private static void validateHobby(String hobby) {
        if (hobby.length() > MAX_HOBBY_LENGTH) {
            throw new LengthErrorException("유저의 취미는 %d자를 넘을 수 없습니다.".formatted(MAX_HOBBY_LENGTH));
        }
        if (!Pattern.matches(HOBBY_REGEX, hobby)) {
            throw new WrongFormatException("올바르지 않은 유저의 취미입니다.");
        }
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    private int age = 0;

    @Column(length = 30)
    private String hobby;

    protected User() {
    }

    public User(String name, int age, String hobby) {
        validateAge(age);
        validateName(name);
        validateHobby(hobby);

        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        post.setAuthor(this);
    }

    public AuthorDto toAuthorDto() {
        return AuthorDto.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public void setName(String name) {
        validateName(name);
        this.name = name;
    }

    public void setAge(int age) {
        validateAge(age);
        this.age = age;
    }

    public void setHobby(String hobby) {
        validateHobby(hobby);
        this.hobby = hobby;
    }
}
