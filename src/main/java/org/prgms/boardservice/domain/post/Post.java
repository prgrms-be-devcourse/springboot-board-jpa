package org.prgms.boardservice.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.prgms.boardservice.domain.BaseTime;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_CONTENT;
import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_TITLE;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    public Post(String title, String content, Long userId) {
        validateTitleLength(title);
        validateContentLength(content);

        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public void changeTitle(String title) {
        validateTitleLength(title);
        this.title = title;
    }

    public void changeContent(String content) {
        validateContentLength(content);
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
}
