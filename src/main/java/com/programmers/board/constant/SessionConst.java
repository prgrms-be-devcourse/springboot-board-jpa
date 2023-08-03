package com.programmers.board.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SessionConst {
    public static final String LOGIN_USER_ID = "userId";
    public static final String NO_LOGIN = "세션 정보가 없습니다";
}
