package org.prgrms.springbootboard.domain;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Post extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private User writer;

    protected Post() {
    }

    @Builder
    public Post(String title, String content, User writer) {
        this.title = title;
        this.content = content;
        this.setWriter(writer);
    }

    public void setWriter(User writer) {
        if (Objects.nonNull(this.writer)) {
            this.writer.getPosts().remove(this);
        }

        this.writer = writer;
        writer.getPosts().add(this);
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
