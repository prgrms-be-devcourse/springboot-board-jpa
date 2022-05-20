package com.su.gesipan.user;

import com.su.gesipan.post.Post;
import com.su.gesipan.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

public interface UserDto {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Create {
        @Size(max = 20)
        private String name;
        @PositiveOrZero
        private Long age;
        @Size(max = 50)
        private String hobby;

        public static Create of(String name, Long age, String hobby) {
            return new Create(name, age, hobby);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Result {
        private Long id;
        private String name;
        private Long age;
        private String hobby;
        private List<PostDto.Result> posts;
        public static Result of(Long id, String name, Long age, String hobby, List<PostDto.Result> posts) {
            return new Result(id, name, age, hobby, posts);
        }
    }
}
