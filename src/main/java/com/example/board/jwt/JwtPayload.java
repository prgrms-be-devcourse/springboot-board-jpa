package com.example.board.jwt;

import jakarta.validation.Payload;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class JwtPayload implements Payload {

    private final Long userId;
    private final List<String> roles;

    public Map<String, ?> toMap() {
        return new HashMap<>() {{
            put("userId", userId);
            put("roles", roles);
        }};
    }

    @Override
    public String toString() {
        return "JwtPayload{" +
                "userId='" + userId + '\'' +
                ", roles=" + roles +
                '}';
    }
}
