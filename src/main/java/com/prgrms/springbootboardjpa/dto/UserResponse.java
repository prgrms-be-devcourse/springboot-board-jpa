package com.prgrms.springbootboardjpa.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Long id;
    private String name;
    private Integer age;
    private String hobby;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
