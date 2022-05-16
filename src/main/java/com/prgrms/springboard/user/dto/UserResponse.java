package com.prgrms.springboard.user.dto;

import java.util.List;

import com.prgrms.springboard.post.dto.PostResponse;
import com.prgrms.springboard.user.domain.User;

import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String name;
    private int age;
    private String hobby;
    private List<PostResponse> postResponses;

    protected UserResponse() {
    }

    public UserResponse(Long id, String name, int age, String hobby, List<PostResponse> postResponses) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postResponses = postResponses;
    }

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getName().getName(),
            user.getAge().getAge(),
            user.getHobby().getHobby(),
            user.getPosts().stream()
                .map(PostResponse::from)
                .toList()
        );
    }
    
}
