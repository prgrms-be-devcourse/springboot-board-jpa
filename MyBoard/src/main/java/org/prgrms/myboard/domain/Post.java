package org.prgrms.myboard.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.myboard.dto.PostResponseDto;

import java.util.Objects;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    private static final int MAX_TITLE_LENGTH = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String content, User user) {
        validateTitle(title);
        this.title = title;
        this.content = content;
        allocateUser(user);
    }

    private void allocateUser(User user) {
        if(Objects.nonNull(this.user)) {
            user.removePost(this);
        }
        this.user = user;
        this.createdBy = user.getName();
        user.writePost(this);
    }

    public void update(String title, String content) {
        if(title != null) {
            this.title = title;
        }
        if(content != null) {
            this.content = content;
        }
    }

    public PostResponseDto toPostResponseDto() {
        return new PostResponseDto(id, title, content, createdBy, getCreatedAt(), getUpdatedAt());
    }

    private void validateTitle(String title) {
        if(title.length() >= MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목의 길이는 30글자 이하입니다.");
        }
    }
}
