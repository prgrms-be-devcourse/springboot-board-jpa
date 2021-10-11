package com.prgrms.board.domain.user;

import com.prgrms.board.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Lob
    private String hobby;

    protected User() {
    }

    @Builder
    public User(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        setCreatedAt(LocalDateTime.now());
        setCreatedBy(name);
    }

    public void changeHobby(String hobby) {
        this.hobby = hobby;
    }
}
