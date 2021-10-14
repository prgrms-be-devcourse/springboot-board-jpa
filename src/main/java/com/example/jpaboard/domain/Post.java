package com.example.jpaboard.domain;

import com.example.jpaboard.dto.PostRequest;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setAuthor(User author) {
        this.author = author;
        author.getPosts().add(this);
    }

    public static Post createPost(User user, String title, String content) {
        Post post = new Post(title, content);
        post.setAuthor(user);
        return post;
    }

    public Post update(PostRequest postRequest) {
        title = postRequest.getTitle();
        content = postRequest.getContent();
        return this;
    }
}
