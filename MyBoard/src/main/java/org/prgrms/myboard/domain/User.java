package org.prgrms.myboard.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.prgrms.myboard.dto.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

import static org.prgrms.myboard.util.ErrorMessage.WRONG_AGE_MESSAGE;
import static org.prgrms.myboard.util.ErrorMessage.WRONG_NAME_MESSAGE;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    private static final int MAX_NAME_LENGTH = 4;
    private static final int MIN_AGE = 0;
    private static final int MAX_AGE = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Nullable
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Nullable
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, int age, String hobby) {
        validateName(name);
        validateAge(age);
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    private void validateName(String name) {
        if(name == null || name.isBlank() || name.length() >= MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(WRONG_NAME_MESSAGE);
        }
    }

    private void validateAge(int age) {
        if(age <= MIN_AGE || age >= MAX_AGE) {
            throw new IllegalArgumentException(WRONG_AGE_MESSAGE);
        }
    }

    public UserResponseDto toUserResponseDto() {
        return new UserResponseDto(id, name, age, hobby, posts, getCreatedAt(), getUpdatedAt());
    }

    public void writePost(Post post) {
        posts.add(post);
    }

    public void removePost(Post post) {
        posts.remove(post);
    }
}
