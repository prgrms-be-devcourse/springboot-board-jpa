package com.example.board.jwt;

import jakarta.validation.Payload;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtPayload implements Payload {

    private final Long id;
    private final List<String> roles;

    public Map<String, ?> toMap() {
        return new HashMap<>() {{
            put("id", id);
            put("roles", roles);
        }};
    }

    public static JwtPayload of(Long id, List<String> roles) {
        return new JwtPayload(id, roles);
    }

    @Override
    public String toString() {
        return "JwtPayload{" +
                "id='" + id + '\'' +
                ", roles=" + roles +
                '}';
    }
}
