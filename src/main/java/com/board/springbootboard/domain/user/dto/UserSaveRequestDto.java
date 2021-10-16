package com.board.springbootboard.domain.user.dto;

import com.board.springbootboard.domain.posts.PostsEntity;
import com.board.springbootboard.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserSaveRequestDto {

    private String name;
    private String nickName;
    private int age;

    @Builder
    public UserSaveRequestDto(String name, String nickName, int age) {
        this.name = name;
        this.nickName = nickName;
        this.age = age;
    }

    // Converter
    // Controller -> Service -> Repository
    //                      DTO (temp)
    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .nickName(nickName)
                .age(age)
                .build();
    }

}
