package com.prgrms.java.domain;

import jakarta.persistence.*;

@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private HobbyType hobby;
}
