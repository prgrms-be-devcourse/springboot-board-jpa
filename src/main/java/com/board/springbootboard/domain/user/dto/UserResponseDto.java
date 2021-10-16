package com.board.springbootboard.domain.user.dto;


import com.board.springbootboard.domain.posts.PostsEntity;
import com.board.springbootboard.domain.user.UserEntity;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String nickName;

    public UserResponseDto(UserEntity userEntity) {
        this.id= userEntity.getId();
        this.name = userEntity.getName();
        this.nickName = userEntity.getNickName();
    }
}
