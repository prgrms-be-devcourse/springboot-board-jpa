package org.programmers.project_board.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setTitle(String title) {
        Assert.notNull(title, "Title should not be null.");
        Assert.isTrue(title.length() <= 50, "Title should be less than 50 characters in length.");

        this.title = title;
    }

    public Post update(String title, String content) {
        setTitle(title);
        setContent(content);

        return this;
    }

}

