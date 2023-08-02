package com.programmers.jpa_board.user.domain.dto.response;

import com.programmers.jpa_board.post.domain.Post;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class CreateUserResponse {
    private Long id;
    private String name;
    private int age;
    private String hobby;
    private List<Post> posts;
    private LocalDateTime createAt;

    @Builder
    public CreateUserResponse(Long id, String name, int age, String hobby, List<Post> posts, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.posts = posts;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getHobby() {
        return hobby;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }
}
