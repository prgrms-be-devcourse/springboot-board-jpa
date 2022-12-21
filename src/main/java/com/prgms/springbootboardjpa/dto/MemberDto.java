package com.prgms.springbootboardjpa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
    private Long memberId;
    private String name;
    private int age;
    private String hobby;
}