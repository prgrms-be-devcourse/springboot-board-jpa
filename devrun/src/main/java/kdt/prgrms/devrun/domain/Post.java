package kdt.prgrms.devrun.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;

        changeUser(user);

        changeCreatedBy(user.getLoginId());
        changeUpdatedBy(user.getLoginId());
    }

    public void changeUser(User user) {
        this.user = user;
        user.addPost(this);
    }

}
