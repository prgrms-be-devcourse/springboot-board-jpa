package org.prgms.boardservice.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.prgms.boardservice.domain.BaseTime;
import org.prgms.boardservice.domain.user.User;

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
        if (title.length() > 20) {
            throw new IllegalArgumentException("제목은 20자 이내여야 합니다.");
        }
    }

    private void validateContentLength(String content) {
        if (content.length() > 500) {
            throw new IllegalArgumentException("내용은 500자 이내여야 합니다.");
        }
    }
}
