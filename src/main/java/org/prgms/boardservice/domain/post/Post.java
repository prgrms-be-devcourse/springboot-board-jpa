package org.prgms.boardservice.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgms.boardservice.domain.BaseTime;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_CONTENT;
import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_TITLE;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @NotBlank
    private String title;

    @Lob
    @NotBlank
    private String content;

    private Long userId;

    public Post(String title, String content) {
        validateTitleLength(title);
        validateContentLength(content);

        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        validateTitleLength(title);
        validateContentLength(content);

        this.title = title;
        this.content = content;
    }

    private void validateTitleLength(String title) {
        if (!hasText(title) || title.length() > 20) {
            throw new IllegalArgumentException(INVALID_POST_TITLE.getMessage());
        }
    }

    private void validateContentLength(String content) {
        if (!hasText(content) || content.length() > 500) {
            throw new IllegalArgumentException(INVALID_POST_CONTENT.getMessage());
        }
    }
}
