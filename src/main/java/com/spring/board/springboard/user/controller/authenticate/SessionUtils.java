package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.domain.dto.MemberDetailResponseDto;
import com.spring.board.springboard.user.exception.AuthenticateException;
import com.spring.board.springboard.user.exception.SessionCreateException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

import static com.spring.board.springboard.user.controller.authenticate.CookieUtils.getUserCookie;

public class SessionUtils {

    private static final Integer SEED_BYTE_NUM = 10;
    private static final StringBuilder stringBuilder = new StringBuilder();
    private static final String ENCODE_MODE = "SHA-256";

    public static void validateMember(HttpServletRequest request, MemberDetailResponseDto memberDetailResponseDto) {
        Cookie cookie = getUserCookie(request);

        Integer findMemberIdBySession = SessionStorage.getExistedMemberId(cookie.getValue());

        if (!Objects.equals(
                memberDetailResponseDto.memberId(), findMemberIdBySession)) {
            throw new AuthenticateException("권한이 없습니다.");
        }
    }

    public static String createSessionId(Integer memberId) {
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

    private static String createSalt() {
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

    private static void resetStringBuilder() {
        stringBuilder.setLength(0);
    }
}
