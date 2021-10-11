package spring.jpa.board.post.domain;

import lombok.Getter;
import lombok.Setter;
import spring.jpa.board.Utils.BaseEntity;
import spring.jpa.board.user.domain.User;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name="content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
