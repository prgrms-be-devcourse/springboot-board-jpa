package com.prgrms.jpaboard.domain.post.dto;

import lombok.Getter;

@Getter
public class UserInfoDto {
    private Long id;
    private String name;

    public UserInfoDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
