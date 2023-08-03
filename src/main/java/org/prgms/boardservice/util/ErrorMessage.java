package org.prgms.boardservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    INVALID_USER_EMAIL_PATTERN("이메일 형식에 맞지 않습니다."),
    INVALID_USER_PASSWORD_PATTERN("비밀번호 형식에 맞지 않습니다."),
    INVALID_USER_NICKNAME_LENGTH("닉네임 길이는 2이상 10이하여야 합니다."),

    NOT_FOUND_USER("해당 아이디의 유저가 존재하지 않습니다."),
    INVALID_POST_TITLE("제목은 20자 이내의 비어있지 않은 문자열이어야 합니다."),
    INVALID_POST_CONTENT("내용은 500자 이내의 비어있지 않은 문자열이어야 합니다.");

    private final String message;
}
