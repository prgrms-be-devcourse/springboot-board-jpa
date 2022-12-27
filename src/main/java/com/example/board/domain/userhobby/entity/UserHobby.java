package com.example.board.domain.userhobby.entity;

import com.example.board.domain.hobby.entity.Hobby;
import com.example.board.domain.user.entity.User;
import com.example.board.global.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.board.global.validator.UserHobbyValidator.validateHobbyNull;
import static com.example.board.global.validator.UserHobbyValidator.validateUserNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_hobby")
@Entity
public class UserHobby extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_hobby_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hobby_id", updatable = false, nullable = false)
    @NotNull
    private Hobby hobby;

    @Builder
    public UserHobby(User user, Hobby hobby) {
        validateUserNull(user);
        validateHobbyNull(hobby);
        this.user = user;
        this.hobby = hobby;
    }

    public void targetUser(User user) {
        this.user = user;
    }
}
