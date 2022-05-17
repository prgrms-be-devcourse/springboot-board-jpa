package com.kdt.springbootboardjpa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDTO{

    private final long postId;

    private String title;

    private String content;

    private final String username;

}
