package com.prgrms.board.user.domain;


import com.prgrms.board.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue
    private Long userId;

    @Column(unique = true)
    private String name;
    private Long age;
    private String hobby;

    @Embedded
    private UserPosts posts = new UserPosts();

    @Builder
    public User(String name, Long age, String hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}