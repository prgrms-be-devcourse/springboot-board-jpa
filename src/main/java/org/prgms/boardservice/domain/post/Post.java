package org.prgms.boardservice.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.prgms.boardservice.domain.BaseTime;

import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_CONTENT;
import static org.prgms.boardservice.util.ErrorMessage.INVALID_POST_TITLE;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
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
