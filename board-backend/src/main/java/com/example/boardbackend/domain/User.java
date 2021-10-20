package com.example.boardbackend.domain;

import com.example.boardbackend.domain.embeded.Email;
import com.example.boardbackend.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", nullable = false, length = 45)
    private String hobby;

    // ---------------------------------------------------------------

    private User(Long id, Email email, String password, String name, int age, String hobby, LocalDateTime createdAt) {
        super(createdAt);
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    static public User of(UserDto userDto){
        return new User(
                userDto.getId(),
                new Email(userDto.getEmail()),
                userDto.getPassword(),
                userDto.getName(),
                userDto.getAge(),
                userDto.getHobby(),
                userDto.getCreatedAt()
        );
    }
}
