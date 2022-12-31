package com.prgrms.springbootboardjpa.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {
    private Long id;
    private String name;
    private Integer age;
    private String hobby;
}
