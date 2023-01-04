package devcourse.board.web.authentication;

import devcourse.board.global.errors.exception.NoLoginMemberException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static devcourse.board.web.authentication.AuthenticationUtil.SESSION_NAME;

@Component
public class SessionManager {

    private final Map<String, Long> sessionStorage = new ConcurrentHashMap<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${cookie.max-age}")
    private int maxSessionAge;

    public void registerNewSession(HttpServletResponse response, Long memberIdentifier) {
        String sessionId = UUID.randomUUID().toString();
        sessionStorage.put(sessionId, memberIdentifier);
        addSessionToResponse(response, sessionId);
    }

    public void expireSession(HttpServletRequest request) {
        Cookie sessionCookie = WebUtils.getCookie(request, SESSION_NAME);
        if (sessionCookie != null) {
            sessionStorage.remove(sessionCookie.getValue());
        }
    }

    public Long getLoginMemberIdentifier(HttpServletRequest request) {
        Cookie sessionCookie = WebUtils.getCookie(request, SESSION_NAME);
        if (sessionCookie == null) {
            throw new NoLoginMemberException("No cookie exists for cookieName=" + SESSION_NAME);
        }

        Long loginMemberIdentifier = sessionStorage.get(sessionCookie.getValue());
        if (loginMemberIdentifier == null) {
            throw new NoLoginMemberException("No session exists for sessionId=" + sessionCookie.getValue());
        }
        return loginMemberIdentifier;
    }

    private void addSessionToResponse(HttpServletResponse response, String cookieValue) {
        Cookie cookie = new Cookie(SESSION_NAME, cookieValue);
        cookie.setPath("/");
        cookie.setMaxAge(maxSessionAge);
        response.addCookie(cookie);
    }
}
