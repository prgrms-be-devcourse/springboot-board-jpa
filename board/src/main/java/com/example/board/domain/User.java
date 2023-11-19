package com.example.board.domain;

import com.example.board.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String hobby;

    @CreatedDate
    private LocalDateTime createdAt;

    public static User toEntity(UserDto.Request request) {
        return User.builder()
                .name(request.name())
                .age(request.age())
                .hobby(request.hobby())
                .build();
    }

    public void changeUserInfo(String name, int age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
