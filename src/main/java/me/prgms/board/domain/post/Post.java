package me.prgms.board.domain.post;

import me.prgms.board.domain.BaseEntity;
import me.prgms.board.domain.User;
import me.prgms.board.domain.post.Title;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
public class Post extends BaseEntity {

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

    public Post() {
    }

    public Post(String title, String content, User user) {
        super("createdBy-yanju", LocalDateTime.now());
        this.title = new Title(title);
        this.content = new Content(content);
        this.user = user;
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

    public void changeTitle(String title) {
        this.title.changeTitle(title);
    }

    public void changeContent(String content) {
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
