package com.example.springbootboard.dto;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.Title;
import com.example.springbootboard.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestCreatePost {

    private Long userId;
    private String title;
    private String content;

    @Builder
    public RequestCreatePost(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Post toEntity(User user) {
        return Post.createPost(new Title(this.title), this.content, user);
    }
}
