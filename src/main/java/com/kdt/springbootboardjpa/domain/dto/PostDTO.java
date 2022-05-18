package com.kdt.springbootboardjpa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO{

    private long postId;

    private String title;

    private String content;

    private String username;

}
