package com.prgrms.jpa.controller.dto.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetByIdUserResponse {

    private Long id;
    private String name;
    private int age;
    private String hobby;

    @Builder
    public GetByIdUserResponse(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
