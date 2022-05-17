package com.kdt.springbootboardjpa.domain.dto;

import lombok.Data;

@Data
public class PostCreateRequest {

    private String title;

    private String content;

    private String username;

}
