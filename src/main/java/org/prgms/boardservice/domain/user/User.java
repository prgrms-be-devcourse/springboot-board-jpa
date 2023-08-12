package org.prgms.boardservice.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgms.boardservice.domain.BaseTime;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_USER_NICKNAME_LENGTH;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id", callSuper = false)
public class User extends BaseTime {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(length = 10, unique = true)
    @NotBlank
    private String nickname;

    public User(Email email, Password password, String nickname) {
        validateNicknameLength(nickname);

        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    private void validateNicknameLength(String nickname) {
        if (nickname.length() < 2 || nickname.length() > 10) {
            throw new IllegalArgumentException(INVALID_USER_NICKNAME_LENGTH.getMessage());
        }
    }
}
