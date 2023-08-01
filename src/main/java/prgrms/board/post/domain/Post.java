package prgrms.board.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import prgrms.board.global.domain.BaseEntity;
import prgrms.board.global.exception.ErrorCode;
import prgrms.board.post.exception.IllegalPostDataException;
import prgrms.board.user.domain.User;

import java.util.Objects;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    private static final int MAXIMUM_LENGTH = 50;

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false, length = 50)
    private String createdBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content) {
        validateTitle(title);
        validateContent(content);

        this.title = title;
        this.content = content;
    }

    public void updateUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
        String name = user.getName();
        updateCreatedBy(name);
    }

    private void validateTitle(String title) {
        if (!StringUtils.hasText(title) ||
                title.length() > MAXIMUM_LENGTH) {
            throw new IllegalPostDataException(ErrorCode.EMPTY_POST_TITLE);
        }
    }

    private void validateContent(String content) {
        if (!StringUtils.hasText(content)) {
            throw new IllegalPostDataException(ErrorCode.EMPTY_POST_CONTENT);
        }
    }

    private void updateCreatedBy(String createdBy) {
        if (!StringUtils.hasText(createdBy) ||
                createdBy.length() > MAXIMUM_LENGTH) {
            throw new IllegalPostDataException(ErrorCode.INVALID_CREATED_BY_VALUE);
        }
        this.createdBy = createdBy;
    }
}
