package com.prgrms.board.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MemberResponseDto {
    private String name;

    private int age;

    private String hobby;

    private List<PostResponseDto> posts;
}
