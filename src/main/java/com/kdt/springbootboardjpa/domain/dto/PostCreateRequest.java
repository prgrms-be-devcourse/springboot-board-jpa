package com.kdt.springbootboardjpa.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    private String title;

    private String content;

    private String username;

}
