package devcource.hihi.boardjpa.domain;

import devcource.hihi.boardjpa.dto.post.CreatePostDto;
import devcource.hihi.boardjpa.dto.post.ResponsePostDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @NotNull
    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Builder
    public Post(String title, String content,User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void allocateUser(User user) {
        if(Objects.nonNull(this.user)) {
            user.getPostList().remove(this);
        }
        this.user = user;
        user.getPostList().add(this);
    }

    public static ResponsePostDto toResponseDto(Post post) {
        return new ResponsePostDto(post.getId(),post.getTitle(), post.getContent(), post.getUser(),post.getCreated_at(),post.getUpdated_at());
    }
    public static CreatePostDto toCreateDto(Post post) {
        return new CreatePostDto(post.getTitle(), post.getContent(),  post.getUser());
    }

}
