package com.example.springbootboardjpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @NotNull
    @Positive(message = "나이는 0보다 커야합니다.")
    private Integer age;

    @Length(max = 50, message = "유효 글자 수를 초과하였습니다.")
    @Column(length = 50)
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // JSonManagedReference, JsonBackReference
    private final List<Post> posts = new ArrayList<>();

    public User(String name, Integer age, String hobby) {
        this(null, name, age, hobby);
    }

    public User(String name, Integer age) {
        this(name, age, null);
    }

    public User(Long id, String name, Integer age) {
        this(id ,name, age, null);
    }

    public void changeName(String name) {
        this.name = name;
    }

}
