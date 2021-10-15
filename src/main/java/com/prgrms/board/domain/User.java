package com.prgrms.board.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "age", nullable = false, length = 20)
    private int age;

    @Column(name = "hobby", length = 100)
    private String hobby;

    public User(){
    }

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
