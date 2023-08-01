package org.prgms.boardservice.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.prgms.boardservice.domain.BaseTime;
import org.prgms.boardservice.domain.user.User;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_CONTENT;
import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_TITLE;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @NotBlank
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotBlank
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content) {
        validateTitleLength(title);
        validateContentLength(content);

        this.title = title;
        this.content = content;
    }

    private void validateTitleLength(String title) {
        if (title == null || title.trim().length() == 0 || title.length() > 20) {
            throw new IllegalArgumentException(INVALID_POST_TITLE.getMessage());
        }
    }

    private void validateContentLength(String content) {
        if (content == null || content.trim().length() == 0 || content.length() > 500) {
            throw new IllegalArgumentException(INVALID_POST_CONTENT.getMessage());
        }
    }

    public void setUser(User user) {
        this.user = user;
    }
}
