package com.example.springbootboard.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "hobby")
    @Enumerated(EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> postList = new ArrayList<>();

    @Builder
    public User(Long id,String name, int age, Hobby hobby){
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        setCreatedAt(LocalDateTime.now());
    }

    public void changeHobby(Hobby hobby){
        this.hobby = hobby;
    }
}
