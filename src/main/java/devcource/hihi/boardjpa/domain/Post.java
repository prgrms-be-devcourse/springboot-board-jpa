package devcource.hihi.boardjpa.domain;

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
    @JoinColumn(name = "created_user_id")
    private User created_by;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void allocateUser(User user) {
        if(Objects.nonNull(created_by)) {
            user.getPostList().remove(this);
        }
        created_by = user;
        user.getPostList().add(this);
    }
}
