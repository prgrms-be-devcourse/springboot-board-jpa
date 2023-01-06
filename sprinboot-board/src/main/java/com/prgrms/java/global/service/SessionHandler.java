package com.prgrms.java.global.service;

import com.prgrms.java.domain.Email;
import com.prgrms.java.global.exception.AuthenticationFailedException;
import com.prgrms.java.global.model.MySession;
import com.prgrms.java.global.model.SessionDto;
import com.prgrms.java.global.util.CookieUtil;
import com.prgrms.java.global.util.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static com.prgrms.java.global.model.MySession.SESSION_ID;

@Component
public class SessionHandler {

    private final Map<String, MySession> sessionMap = new ConcurrentHashMap<>();

    public String resolve(SessionDto sessionDto) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Optional<String> maybeSessionId = getSessionId(sessionDto.email());
        if (maybeSessionId.isPresent()) {
            return maybeSessionId.get();
        }

        final MySession session = SessionUtil.generateValue(sessionDto);
        String sessionId = SessionUtil.generateId();
        sessionMap.put(sessionId, session);
        return sessionId;
    }

    private void validSessionId(String sessionId) {
        if (!isSaved(sessionId)) {
            throw new AuthenticationFailedException(MessageFormat.format("Invalid Session ID. Please check session id. [Session ID]: {0}", sessionId));
        }
    }

    private boolean isSaved(String sessionId) {
        return sessionMap.containsKey(sessionId);
    }

    private Optional<String> getSessionId(Email email) {
        return sessionMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue()
                        .email().equals(email))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public MySession find(HttpServletRequest httpServletRequest) {
        String sessionId = CookieUtil.getValue(httpServletRequest, SESSION_ID);
        validSessionId(sessionId);

        return sessionMap.get(sessionId);
    }

    public void delete(HttpServletRequest httpServletRequest) {
        final String sessionId = CookieUtil.getValue(httpServletRequest, SESSION_ID);
        validSessionId(sessionId);

        sessionMap.remove(sessionId);
    }
}
