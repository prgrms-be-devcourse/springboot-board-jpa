package com.prgrms.dev.springbootboardjpa.domain.user;

import com.prgrms.dev.springbootboardjpa.domain.BaseEntity;
import com.prgrms.dev.springbootboardjpa.domain.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "hobby", nullable = false)
    @Enumerated(EnumType.STRING)
    private Hobby hobby; // 자유도 높은 컬럼, Enum 위험 -> 관리하는데 조심, MySQL에서의 ENUM 관리

    @Column(name = "age", nullable = false)
    private int age; // 나이 검증

    // cascadeType -> ...
    // 연관 관계, cascade, orphan
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts = new ArrayList<>(); // 다른 의견

    @Builder
    public User(String name, String hobby, int age) {
        validateNull(name);
        validateAge(age);

        this.name = name;
        this.hobby = Hobby.getHobby(hobby);
        this.age = age;
    }

    private void validateNull(String input) {
        if(input.isEmpty() || input.isBlank()) throw new NullPointerException("NPE");
    }

    private void validateAge(int age) {
        if(age <= 0 || age >= 200) throw new NullPointerException("NPE");
    }

}
