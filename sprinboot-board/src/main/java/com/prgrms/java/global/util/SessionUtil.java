package com.prgrms.java.global.util;

import com.prgrms.java.global.model.MySession;
import com.prgrms.java.global.model.SessionDto;
import org.springframework.http.ResponseCookie;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import static com.prgrms.java.global.model.MySession.SESSION_ID;

public class SessionUtil {

    public static MySession generateValue(SessionDto sessionDto) {
        return new MySession(sessionDto.email(), sessionDto.userId());
    }

    public static String generateId() {
        SecureRandom secureRandom = new SecureRandom();
        final byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        final byte[] encoded = Base64.getEncoder().encode(bytes);
        return new String(encoded, StandardCharsets.UTF_8);
    }

    public static ResponseCookie getNewCookie(String sessionId) {
        return ResponseCookie.from(SESSION_ID, sessionId)
                .maxAge(60 * 15L)
                .path("/")
                .httpOnly(true)
                .build();
    }

    public static ResponseCookie getDeletedCookie() {
        return ResponseCookie.from(SESSION_ID, null)
                .maxAge(0L)
                .path("/")
                .build();
    }
}
