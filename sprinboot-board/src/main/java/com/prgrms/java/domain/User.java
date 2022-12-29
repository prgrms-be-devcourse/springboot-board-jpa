package com.prgrms.java.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @NotNull
    @Size(min = 1, max = 30, message = "name must be over 0 and under 31.")
    private String name;

    @Column(unique = true, nullable = false, length = 30)
    @NotNull
    @Size(min = 1, max = 30, message = "email must be over 0 and under 31.")
    @Email(regexp = com.prgrms.java.domain.Email.EMAIL_REGEX)
    private String email;

    @Column(unique = true, nullable = false, length = 30)
    @NotNull
    @Size(min = 1, max = 30, message = "password must be over 0 and under 31.")
    private String password;

    @Positive(message = "age must be over 0.")
    private int age;

    @Enumerated(EnumType.STRING)
    private HobbyType hobby;

    protected User() {
    }

    public User(Long id, String name, String email, String password, int age, HobbyType hobby) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.hobby = hobby;
    }

    public User(String name, String email, String password, int age, HobbyType hobby) {
        this(null, name, email, password, age, hobby);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public HobbyType getHobby() {
        return hobby;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
