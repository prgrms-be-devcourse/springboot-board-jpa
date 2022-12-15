package com.prgrms.be.app.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity{
    @Id @GeneratedValue
    private Long id;

    @NotBlank
    @Max(value = 17)
    private String name;

    @NotBlank
    private int age;

    private String hobby;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    public User(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
