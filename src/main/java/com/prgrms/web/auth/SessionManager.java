package com.prgrms.web.auth;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.prgrms.global.error.ErrorCode;
import com.prgrms.global.exception.AuthenticationFailedException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SessionManager {

    public final String SESSION_NAME = "userId";

    private final Map<String, Long> sessionStorage = new ConcurrentHashMap<>();

    private Cookie createSessionCookie(Long userId) {

        String sessionStorageKey = String.valueOf(UUID.randomUUID());
        Cookie session = new Cookie(SESSION_NAME, sessionStorageKey);
        session.setMaxAge(1800);
        session.setPath("/");

        sessionStorage.put(sessionStorageKey, userId);

        return session;
    }

    public void removeSession(HttpServletRequest request, HttpServletResponse response) {

        Cookie session = findSession(request);
        sessionStorage.remove(session.getName());

        session.setValue(null);
        session.setMaxAge(0);
        session.setPath("/");

        response.addCookie(session);
    }

    private Cookie findSession(HttpServletRequest request) {

        Cookie session = WebUtils.getCookie(request, SESSION_NAME);

        if (session == null || session.getValue() == null) {
            throw new AuthenticationFailedException(ErrorCode.AUTHENCTICATION_FAILED);
        }

        return session;
    }

    public Long getSession(HttpServletRequest request) {

        Cookie session = findSession(request);

        return sessionStorage.get(session.getValue());
    }

    public void createSession(Long userId, HttpServletRequest request, HttpServletResponse response) {

        Cookie session = WebUtils.getCookie(request, SESSION_NAME);

        if (session != null) {
            removeSession(request, response);
        }

        response.addCookie(createSessionCookie(userId));
    }

}
