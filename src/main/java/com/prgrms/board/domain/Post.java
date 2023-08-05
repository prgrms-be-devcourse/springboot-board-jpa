package com.prgrms.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();
    private String title;
    private String content;


    public Post(Users user, String title, String content) {
        validateContent(content);
        validateTitle(title);
        this.user = user;
        this.title = title;
        this.content = content;

        List<Post> posts = user.getPosts();
        posts.add(this);
    }

    public void updatePost(String title, String content) {
        if(!title.isEmpty()) {
            validateTitle(title);
            this.title = title;
        }
        if(!content.isEmpty()) {
            validateContent(content);
            this.content = content;
        }
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목이 비어있습니다.");
        }
    }
    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("게시글 내용을 입력해주세요.");
        }
    }
}