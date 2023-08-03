package org.prgms.boardservice.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgms.boardservice.domain.BaseTime;
import org.prgms.boardservice.domain.post.Post;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.prgms.boardservice.util.ErrorMessage.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 20, unique = true)
    @NotBlank
    private String email;

    @Column(length = 100)
    @NotBlank
    private String password;

    @Column(length = 10, unique = true)
    @NotBlank
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    public User(String email, String password, String nickname) {
        validateEmailPattern(email);
        validatePasswordPattern(password);
        validateNicknameLength(nickname);

        this.email = email;
        this.password = encodePassword(password);
        this.nickname = nickname;
    }

    private void validateEmailPattern(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        if (!Pattern.matches(regex, email)) {
            throw new IllegalArgumentException(INVALID_USER_EMAIL_PATTERN.getMessage());
        }
    }

    private void validatePasswordPattern(String password) {
        String regex = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";

        if (!Pattern.matches(regex, password)) {
            throw new IllegalArgumentException(INVALID_USER_PASSWORD_PATTERN.getMessage());
        }
    }

    private void validateNicknameLength(String nickname) {
        if (nickname.length() < 2 || nickname.length() > 10) {
            throw new IllegalArgumentException(INVALID_USER_NICKNAME_LENGTH.getMessage());
        }
    }

    private void addPost(Post post) {
        if (Objects.nonNull(post.getUser())) {
            throw new RuntimeException("게시글의 작성자는 바꿀 수 없습니다.");
        }

        posts.add(post);
        post.setUser(this);
    }

    private String encodePassword(String password) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }
}
