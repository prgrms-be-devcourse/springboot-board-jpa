package com.prgrms.springbootboardjpa.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {
    private String title;
    private String content;
    @Setter private Object user;
}
