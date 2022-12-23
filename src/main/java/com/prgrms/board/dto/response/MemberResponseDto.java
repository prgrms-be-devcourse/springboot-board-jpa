package com.prgrms.board.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberResponseDto {
    private Long id;

    private String name;

    private int age;

    private String hobby;

    private List<PostResponseDto> posts;
}
