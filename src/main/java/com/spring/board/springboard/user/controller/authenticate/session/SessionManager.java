package com.spring.board.springboard.user.controller.authenticate.session;

import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static com.spring.board.springboard.user.controller.authenticate.cookie.CookieUtils.getUserCookie;

@Component
public class SessionManager {

    private static final Logger logger = LoggerFactory.getLogger(SessionManager.class);

    private static final Integer SEED_BYTE_NUM = 10;
    private static final String ENCODE_MODE = "SHA-256";

    private final SessionStorage sessionStorage;

    public SessionManager(SessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public Session findSession(HttpServletRequest request) {
        Cookie cookie = getUserCookie(request);
        return sessionStorage.getSession(cookie.getValue());
    }

    public Session getSessionOrCreateIfNotExist(MemberDetailResponseDto memberDetailResponseDto) {
        return sessionStorage.getSessionByEmail(memberDetailResponseDto.email())
                .orElseGet(() -> {
                    String newSessionId = createSessionId(memberDetailResponseDto.email());
                    Session session = Session.create(
                            newSessionId,
                            memberDetailResponseDto.email());
                    sessionStorage.putSession(session);
                    return session;
                });
    }

    private String createSessionId(String email) {
        StringBuilder stringBuilder = new StringBuilder();
        String salt = createSalt();
        stringBuilder.append(email);
        stringBuilder.append(salt);

        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCODE_MODE);
            byte[] sessionBytes = stringBuilder.toString()
                    .getBytes();
            messageDigest.update(sessionBytes);
            sessionBytes = messageDigest.digest();

            for (byte sessionByte : sessionBytes) {
                stringBuilder.append(
                        String.format("%01x", sessionByte));
            }
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            logger.error("세션을 생성할 수 없습니다.", noSuchAlgorithmException);
        }

        return stringBuilder.toString();
    }

    private String createSalt() {
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        byte[] generatedSalts = new byte[SEED_BYTE_NUM];
        secureRandom.nextBytes(generatedSalts);


        for (byte salt : generatedSalts) {
            stringBuilder.append(
                    String.format("%01x", salt));
        }

        return stringBuilder.toString();
    }

    public void delete(Session session) {
        sessionStorage.remove(session);
    }
}
