package com.programmers.board.dto;

import com.programmers.board.entity.Post;
import com.programmers.board.entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Convertor {
    public static Post postRequestConvertor(PostRequestDto postRequestDto) {
        if (postRequestDto.getTitle() == null) {
            throw new IllegalArgumentException("제목이 없습니다.");
        }
        if (postRequestDto.getContent() == null) {
            throw new IllegalArgumentException("내용이 없습니다.");
        }
        if (postRequestDto.getUserId() == null) {
            throw new IllegalArgumentException("유저 정보가 없습니다.");
        }
        return new Post(postRequestDto.getTitle(), postRequestDto.getContent());
    }

    public static PostResponseDto postResponseConvertor(Post post) {
        return new PostResponseDto(post.getTitle(), post.getContent(), post.getCreatedAt().toString(), post.getCreatedBy());
    }
    public static User userRequestConvertor(UserRequestDto userRequestDto) {
        if (userRequestDto.getName() == null) {
            throw new IllegalArgumentException("이름이 없습니다.");
        }
        if (userRequestDto.getAge() == null) {
            throw new IllegalArgumentException("나이가 없습니다.");
        }
        if (userRequestDto.getHobby() == null) {
            throw new IllegalArgumentException("취미가 없습니다.");
        }
        return new User(userRequestDto.getName(), userRequestDto.getAge(), userRequestDto.getHobby());
    }
}
