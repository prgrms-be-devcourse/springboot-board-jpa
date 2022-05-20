package com.su.gesipan.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.su.gesipan.post.Post;
import com.su.gesipan.post.PostDto;
import com.su.gesipan.user.User;
import com.su.gesipan.user.UserDto;

public abstract class Helper {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static User makeUser() {
        return User.of("su", 24L, "놀기");
    }

    public static Post makePost() {
        return Post.of("제목", "본문");
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
