package com.prgrms.be.app.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 17)
    private String name;

    @NotNull
    @Min(value = 1)
    @Max(value = 120)
    private Integer age;

    private String hobby;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public User(String name, Integer age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void addPost(Post post) {
        post.setCreatedBy(this);
    }
}
