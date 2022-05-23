package org.programmers.board.entity;

import org.programmers.board.entity.vo.Content;
import org.programmers.board.entity.vo.Title;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "board")
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User user;

    protected Board() {
    }

    public Board(Title title, Content content, User user) {
        super(LocalDateTime.now());
        this.title = title;
        this.content = content;
        setUser(user);
    }

    public void editBoard(String title, String content) {
        this.title = new Title(title);
        this.content = new Content(content);
    }

    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getBoards().remove(this);
        }

        this.user = user;
        user.getBoards().add(this);
    }
}