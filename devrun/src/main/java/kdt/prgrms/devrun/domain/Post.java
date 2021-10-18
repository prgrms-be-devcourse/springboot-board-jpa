package kdt.prgrms.devrun.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", length = 200, nullable = false)
    private String title;

    @Lob
    @Column(name = "content", length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_post_to_user"))
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;

        changeUser(user);
    }

    public void changeUser(User user) {
        this.user = user;
        user.addPost(this);
    }

    public Post updatePost(String title, String content) {
        this.title = title;
        this.content = content;

        return this;
    }

}
