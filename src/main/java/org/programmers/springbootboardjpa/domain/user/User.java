package org.programmers.springbootboardjpa.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.springbootboardjpa.domain.BaseTimeEntity;

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

    public User update(User userAfterFormApplied) {
        this.nickname = userAfterFormApplied.getNickname();
        this.password = userAfterFormApplied.getPassword();
        this.name = userAfterFormApplied.getName();
        this.age = userAfterFormApplied.getAge();
        this.interests = userAfterFormApplied.getInterests();
        return this;
    }
}