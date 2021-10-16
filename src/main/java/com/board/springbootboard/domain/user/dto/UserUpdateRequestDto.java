package com.board.springbootboard.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private Long id;

    private String name;
    private String nickName;

    @Builder
    public UserUpdateRequestDto(String name, String nickName) {
        this.name = name;
        this.nickName = nickName;
    }

}
