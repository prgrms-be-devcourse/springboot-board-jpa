package org.prgrms.myboard.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.prgrms.myboard.dto.UserResponseDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull(message = "id는 null이면 안됩니다.")
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "이름이 비어있습니다.")
    @Column(nullable = false)
    private String name;

    @Min(value = 1, message = "나이는 최소 한살입니다.")
    @Column(nullable = false)
    private int age;

    @Nullable
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Nullable
    private List<Post> posts = new ArrayList<>();

    @Builder
    public User(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
//        this.posts = new ArrayList<>();
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeHobby(String hobby) {
        this.hobby = hobby;
    }

    public UserResponseDto toUserResponseDto() {
        return new UserResponseDto(id, name, age, hobby, posts, getCreatedAt(), getUpdatedAt());
    }
}
