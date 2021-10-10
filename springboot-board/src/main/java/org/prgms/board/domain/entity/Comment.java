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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post post;

    protected Comment() {
    }

    @Builder
    private Comment(Long id, String content, Post post, User writer) {
        this.id = id;
        this.content = content;
        this.post = post;
        this.writer = writer;
    }

    public void changeInfo(String content) {
        this.content = content;
    }

}
