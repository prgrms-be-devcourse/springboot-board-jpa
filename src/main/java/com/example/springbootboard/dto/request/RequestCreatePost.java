package com.example.springbootboard.dto.request;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.Title;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class RequestCreatePost {
    
    @NotNull(message = "사용자 정보는 필수입니다")
    @JsonProperty("user")
    private UserDto userDto;

    @Size(min = Title.TITLE_MIN_LENGTH, max = Title.TITLE_MAX_LENGTH, message = "게시물 제목의 길이를 확인해주세요")
    @NotBlank(message = "게시물 제목은 필수입니다")
    private String title;

    @NotNull(message = "게시물 내용은 필수입니다")
    private String content;

    @Builder
    public RequestCreatePost(UserDto userDto, String title, String content) {
        this.userDto = userDto;
        this.title = title;
        this.content = content;
    }

    public Post toEntity(User user) {
        return Post.createPost(new Title(this.title), this.content, user);
    }

}
