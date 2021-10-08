package org.prgms.board.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.prgms.board.common.BaseTime;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "comment")
public class Comment extends BaseTime {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    protected Comment() {
    }

    @Builder
    private Comment(Long id, String content, String author, Post post, User user) {
        this.id = id;
        this.content = content;
        this.author = author;
        this.post = post;
        this.user = user;
    }

    public void changeInfo(String content) {
        this.content = content;
    }

}
