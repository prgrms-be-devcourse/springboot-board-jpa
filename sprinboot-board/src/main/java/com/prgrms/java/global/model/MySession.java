package com.prgrms.java.global.model;

import com.prgrms.java.domain.Email;

public record MySession(Email email, long userId) {
    public static final String SESSION_ID = "session_id";
    public static final long MAX_AGE = 60*10L;
}
