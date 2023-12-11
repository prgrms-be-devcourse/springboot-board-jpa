package com.example.board.auth.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@RedisHash(value = "jwtToken", timeToLive = 60 * 60 * 24 * 3)
public class RefreshToken {

    @Id
    private String id;

    private String refreshToken;

    @Indexed
    private String accessToken;
}
