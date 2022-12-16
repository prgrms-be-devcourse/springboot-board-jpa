package com.prgrms.boardjpa.domain.user;

import com.prgrms.boardjpa.domain.BaseEntity;

import javax.persistence.*;

@Entity
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 20)
    private String name;
    private int age;
    @Enumerated
    private Hobby hobby;
}
