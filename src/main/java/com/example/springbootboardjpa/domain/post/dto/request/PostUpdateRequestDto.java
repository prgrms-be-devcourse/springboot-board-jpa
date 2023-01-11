package com.example.springbootboardjpa.domain.post.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PostUpdateRequestDto {

    private String title;
    private String content;
    private long memberId;
}
