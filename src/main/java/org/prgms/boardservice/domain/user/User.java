package org.prgms.boardservice.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.prgms.boardservice.domain.BaseTime;
import org.prgms.boardservice.domain.post.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

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

    @Column(length = 20)
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
        this.password = password;
        this.nickname = nickname;
    }

    private void validateEmailPattern(String email) {
        String regex = "\\\\b[\\\\w\\\\.-]+@[\\\\w\\\\.-]+\\\\.\\\\w{2,4}\\\\b";

        if (!Pattern.matches(regex, email)) {
            throw new IllegalArgumentException("이메일 형식에 맞지 않습니다.");
        }
    }

    private void validatePasswordPattern(String password) {
        String regex = "/^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$/";

        if (!Pattern.matches(regex, password)) {
            throw new IllegalArgumentException("비밀번호 형식에 맞지 않습니다.");
        }
    }

    private void validateNicknameLength(String nickname) {
        if (nickname.length() < 2 || nickname.length() > 10) {
            throw new IllegalArgumentException("닉네임 길이는 2이상 10이하여야 합니다.");
        }
    }

    private void addPost(Post post) {
        if (Objects.nonNull(post.getUser())) {
            throw new RuntimeException("게시글의 작성자는 바꿀 수 없습니다.");
        }

        posts.add(post);
        post.setUser(this);
    }
}
