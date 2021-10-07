package com.kdt.bulletinboard.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "user")
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID userId;

    @Column(name = "name", length = 50, nullable = false)
    private String userName;

    @Column(name = "hobby", length = 50)
    private String hobby;

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String hobby) {
        this.userName = userName;
        this.hobby = hobby;
    }

}
