package com.prgrms.board.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private String memberName;
}
