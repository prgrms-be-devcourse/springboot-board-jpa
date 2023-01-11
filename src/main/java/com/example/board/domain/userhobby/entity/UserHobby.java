package com.example.board.domain.userhobby.entity;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.user.entity.User;
import com.example.board.domain.userhobby.validator.UserHobbyValidator;
import com.example.board.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_hobby")
@Entity
public class UserHobby extends BaseEntity {

    private static final UserHobbyValidator validator = new UserHobbyValidator();

    @Id @GeneratedValue
    @Column(name = "user_hobby_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hobby_id", updatable = false, nullable = false)
    private Hobby hobby;

    @Builder
    public UserHobby(User user, Hobby hobby) {
        validator.validateUserNull(user);
        validator.validateHobbyNull(hobby);
        this.user = user;
        this.hobby = hobby;
    }

    public void targetUser(User user) {
        this.user = user;
    }
}
