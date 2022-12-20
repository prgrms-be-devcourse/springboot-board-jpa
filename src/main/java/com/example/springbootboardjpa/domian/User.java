package com.example.springbootboardjpa.domian;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(length = 50)
    private String hobby;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

}
