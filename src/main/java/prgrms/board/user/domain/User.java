package prgrms.board.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import prgrms.board.global.domain.BaseEntity;
import prgrms.board.global.exception.ErrorCode;
import prgrms.board.post.domain.Post;
import prgrms.board.user.exception.IllegalUserDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    private static final String NAME_REGEX_PATTERN = "^[가-힣a-zA-Z]+$";
    private static final int MAX_AGE = 120;
    private static final int MIN_AGE = 7;
    private static final int MAXIMUM_LENGTH = 50;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private Integer age;
    @Column
    private String hobby;
    @Column(nullable = false, length = 50)
    private String createdBy;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    List<Post> posts = new ArrayList<>();

    public User(String name, Integer age) {
        validateName(name);
        validateAge(age);

        this.name = name;
        this.age = age;
        this.createdBy = name;
    }

    public void updateHobby(String hobby) {
        this.hobby = hobby;
    }

    private void validateAge(Integer age) {
        if (age < MIN_AGE || age > MAX_AGE) {
            throw new IllegalUserDataException(ErrorCode.INVALID_USER_AGE);
        }
    }

    private void validateName(String name) {
        if (!StringUtils.hasText(name) ||
                name.length() > MAXIMUM_LENGTH ||
                !Pattern.matches(NAME_REGEX_PATTERN, name)
        ) {
            throw new IllegalUserDataException(ErrorCode.INVALID_USER_AGE);
        }
    }
}
