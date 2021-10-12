package com.example.boardbackend.dto.response;

import com.example.boardbackend.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardResponse {
    private Long id;
    private String title;
    private Long view;
    private String createdBy;
}
