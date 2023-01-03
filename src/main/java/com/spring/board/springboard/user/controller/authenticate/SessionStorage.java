package com.spring.board.springboard.user.controller.authenticate;

import com.spring.board.springboard.user.exception.AuthenticateException;
import com.spring.board.springboard.user.exception.SessionCreateException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.spring.board.springboard.user.controller.authenticate.SessionUtils.createSessionId;

@Component
public class SessionStorage {

    private static final Map<String, Integer> sessions = new ConcurrentHashMap<>();

    public static Integer getExistedMemberId(String sessionId) {
        if (!sessions.containsKey(sessionId)) {
            throw new AuthenticateException("인증되지 않은 사용자입니다.");
        }
        return sessions.get(sessionId);
    }

    public static String getSessionOrCreateIfNotExist(Integer memberId) {
        if (sessions.containsValue(memberId)) {
            Map<Integer, String> reverseSessionsKeyValue = sessions.entrySet()
                    .stream()
                    .collect(
                            Collectors.toConcurrentMap(
                                    Map.Entry::getValue,
                                    Map.Entry::getKey
                            ));
            return reverseSessionsKeyValue.get(memberId);
        }
        return createSessionId(memberId);
    }

    public static void putSession(String sessionId, Integer memberId) {
        if (sessionId.isEmpty() || sessionId.isBlank()) {
            throw new SessionCreateException("세션이 비었기 때문에 세션 정보 저장에 실패하였습니다.");
        }
        if (Objects.isNull(memberId)) {
            throw new SessionCreateException("멤버 아이디가 비었기 때문에 세션 정보 저장에 실패하였습니다.");
        }

        sessions.put(sessionId, memberId);
    }
}
