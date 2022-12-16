package com.spring.board.springboard.user.domain;

import com.spring.board.springboard.post.domain.dto.PostDTO;

import java.util.List;

public class UserDTO {
    private final String name;
    private final Integer age;
    private final Hobby hobby;
    private final List<PostDTO> postList;

    public UserDTO(String name, Integer age, Hobby hobby, List<PostDTO> postList) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postList = postList;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public List<PostDTO> getPostList() {
        return postList;
    }
}
