package com.prgrms.springbootboardjpa.user.entity;

import com.prgrms.springbootboardjpa.DatetimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class User extends DatetimeEntity {
    //id, name, age, hobby
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private int age;

    @Column
    private String hobby;

    @Embedded
    private Name name;

    @Embedded
    private Password password;

    @Embedded
    private Email email;

}
