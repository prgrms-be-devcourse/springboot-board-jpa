package org.programmers.springbootboardjpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    public User(String nickname, Password password, Name name, Age age) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    public User(String nickname, Password password, Name name, Age age, UserInterests interest) {
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.age = age;
        this.interests = interest;
    }

    public User(Long userId, String nickname, Password password, Name name, Age age, UserInterests interests) {
        this.userId = userId;
        this.nickname = nickname;
        this.password = password;
        this.name = name;
        this.age = age;
        this.interests = interests;
    }

    @Id
    @GeneratedValue
    private Long userId;

    //TODO: 닉네임을 클래스화할 필요성 고려
    @Column(nullable = false)
    private String nickname;

    @Embedded
    @Column(nullable = false)
    private Password password;

    @Embedded
    @Column(nullable = false)
    private Name name;

    @Embedded
    @Column(nullable = false)
    private Age age;

    @Embedded
    private UserInterests interests;

    public User updateUser(User userAfterAppliedForm) {
        this.nickname = userAfterAppliedForm.getNickname();
        this.password = userAfterAppliedForm.getPassword();
        this.name = userAfterAppliedForm.getName();
        this.age = userAfterAppliedForm.getAge();
        this.interests = userAfterAppliedForm.getInterests();
        return this;
    }
}