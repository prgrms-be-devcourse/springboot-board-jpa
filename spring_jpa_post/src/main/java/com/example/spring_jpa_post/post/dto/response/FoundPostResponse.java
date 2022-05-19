package com.example.spring_jpa_post.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FoundPostResponse {
    private Long id;
    private String title;
    private String content;
}
