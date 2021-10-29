package com.kdt.springbootboard.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    private Long userId; // Todo : Post생성시 User 어떻게 set하는게 좋은 방법?
    private String title;
    private String content;
}
