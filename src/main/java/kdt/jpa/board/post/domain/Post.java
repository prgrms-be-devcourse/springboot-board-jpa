package kdt.jpa.board.post.domain;

import jakarta.persistence.*;
import kdt.jpa.board.global.entity.BaseEntity;
import kdt.jpa.board.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void edit(String title, String contents) {
        this.title = title;
        this.content = contents;
    }

    public String getUserName() {
        return user.getName();
    }
}
