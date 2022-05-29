package me.prgms.board.domain.post;

import me.prgms.board.domain.BaseEntity;
import me.prgms.board.domain.User;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    private static final String CREATE_BY = Post.class.toString();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    protected Post() {
    }

    public Post(String title, String content, User user) {
        super(CREATE_BY, LocalDateTime.now());
        this.title = new Title(title);
        this.content = new Content(content);
        validate(user);
        this.user = user;
    }

    private void validate(User user) {
        if (user == null)
            throw new IllegalArgumentException("User는 null일 수 없습니다!");
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content.getContent();
    }

    public User getUser() {
        return user;
    }

    public String getTitle() {
        return title.getTitle();
    }

    public void changePost(String title, String content) {
        this.title.changeTitle(title);
        this.content.setContent(content);
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }
}
