package com.spring.board.springboard.user.domain;

import com.spring.board.springboard.post.domain.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private String name;

    @Column(name = "age", nullable = false)
    @NotNull(message = "나이는 빈 값일 수 없습니다.")
    @Min(value = 10, message = "10살 미만은 서비스 이용이 불가능합니다.")
    private Integer age;

    @Column(name = "hobby")
    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();

    protected Member() {
    }

    public Member(String name, Integer age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public List<Post> getPostList() {
        return new ArrayList<>(postList);
    }
}
