package org.prgms.boardservice.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    INVALID_USER_EMAIL_PATTERN("이메일 형식에 맞지 않습니다."),
    INVALID_USER_PASSWORD_PATTERN("비밀번호 형식에 맞지 않습니다."),
    INVALID_USER_NICKNAME_LENGTH("닉네임 길이는 2이상 10이하여야 합니다."),

    INVALID_POST_TITLE_LENGTH("제목은 20자 이내여야 합니다."),
    INVALID_POST_CONTENT_LENGTH("내용은 500자 이내여야 합니다.");

    private final String message;
}
