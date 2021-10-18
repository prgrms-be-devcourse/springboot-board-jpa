package com.prgrms.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "hobby", length = 100)
    private String hobby;

    @Builder
    private User(Long id, String name, int age, String hobby){
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public void changeUserInfo(String name, int age, String hobby){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

}
