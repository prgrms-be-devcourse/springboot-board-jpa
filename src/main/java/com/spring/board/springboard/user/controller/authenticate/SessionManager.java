package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import com.spring.board.springboard.user.exception.SessionCreateException;
import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Component
public class SessionManager {

    private static final Integer SEED_BYTE_NUM = 10;
    private static final StringBuilder stringBuilder = new StringBuilder();
    private static final String ENCODE_MODE = "SHA-256";

    private final SessionStorage sessionStorage;

    public SessionManager(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public Session findSession(Cookie cookie) {
        return sessionStorage.getSession(cookie.getValue());
    }

    public Session getSessionOrCreateIfNotExist(MemberDetailResponseDto memberDetailResponseDto) {
        return sessionStorage.getSessionByEmail(memberDetailResponseDto.email())
                .orElseGet(() -> {
                    String newSessionId = createSessionId(memberDetailResponseDto.memberId());
                    Session session = Session.create(
                            newSessionId,
                            memberDetailResponseDto.memberId(),
                            memberDetailResponseDto.email());
                    sessionStorage.putSession(session);
                    return session;
                });
    }

    private String createSessionId(Integer memberId) {
        stringBuilder.append(memberId);
        stringBuilder.append(createSalt());

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCODE_MODE);
            byte[] sessionBytes = stringBuilder.toString()
                    .getBytes();
            messageDigest.update(sessionBytes);
            sessionBytes = messageDigest.digest();

            resetStringBuilder();

            for (byte sessionByte : sessionBytes) {
                stringBuilder.append(
                        String.format("%01x", sessionByte));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new SessionCreateException("세션을 생성할 수 없습니다.");
        }

        return stringBuilder.toString();
    }

    private String createSalt() {
        SecureRandom secureRandom = new SecureRandom();

        byte[] generatedSalts = new byte[SEED_BYTE_NUM];
        secureRandom.nextBytes(generatedSalts);


        for (byte salt : generatedSalts) {
            stringBuilder.append(
                    String.format("%01x", salt));
        }

        String salt = stringBuilder.toString();
        resetStringBuilder();
        return salt;
    }

    private void resetStringBuilder() {
        stringBuilder.setLength(0);
    }
}
