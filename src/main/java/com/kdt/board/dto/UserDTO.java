package com.kdt.board.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private Integer age;
    private String hobby;
    private List<PostDTO> postDTOs;
    private LocalDateTime createdAt;
    private String createdBy;
}
