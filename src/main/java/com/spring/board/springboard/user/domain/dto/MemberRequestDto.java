package com.spring.board.springboard.user.domain.dto;

import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberRequestDto(
        @NotBlank(message = "이메일을 반드시 입력해주세요.") String email,
        @NotBlank(message = "비밀번호를 반드시 입력해주세요.") String password,
        @NotBlank(message = "이름을 반드시 입력해주세요.") String name,
        @NotNull(message = "나이를 반드시 입력해주세요.") Integer age,
        Hobby hobby) {

    public MemberRequestDto(Member member) {
        this(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getAge(),
                member.getHobby()
        );
    }

    public Member toEntity() {
        return new Member(email, password, name, age, hobby);
    }
}
