package org.prgrms.myboard.domain;

import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;

import java.util.Objects;

@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull(message = "id는 null이면 안됩니다.")
    @Column(name = "post_id", nullable = false)
    private Long id;

    @NotBlank(message = "제목이 비어있습니다.")
    private String title;

    @NotBlank(message = "내용이 비어있습니다.")
    private String content;

    @Column(name = "created_by")
    @NotBlank(message = "작성자가 비어있으면 안됩니다.")
    private String createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void allocateUser(User user) {
        if(Objects.nonNull(this.user)) {
            user.getPosts().remove(this);
        }
        this.user = user;
        this.createdBy = user.getName();
        user.getPosts().add(this);
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
}
