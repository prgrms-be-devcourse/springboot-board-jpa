package com.example.board.global.security.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.board.global.exception.BusinessException;
import com.example.board.global.exception.ErrorCode;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Jwt {

    private final String issuer;
    private final String clientSecret;
    private final int accessExpirySeconds;
    private final int refreshExpirySeconds;
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;

    public Jwt(String issuer, String clientSecret, int accessExpirySeconds, int refreshExpirySeconds) {
        this.issuer = issuer;
        this.clientSecret = clientSecret;
        this.accessExpirySeconds = accessExpirySeconds;
        this.refreshExpirySeconds = refreshExpirySeconds;
        this.algorithm = Algorithm.HMAC256(clientSecret);
        this.jwtVerifier = com.auth0.jwt.JWT.require(algorithm)
                .withIssuer(issuer)
                .build();
    }

    // 토큰 생성 메서드(토큰을 만드는데 필요한 데이터를 claims로 받음)
    public String sign(Claims claims, String tokenType) {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        builder.withIssuer(issuer);
        builder.withIssuedAt(new Date());
        if (tokenType.equals("access")) {
            if (accessExpirySeconds > 0) {
                builder.withExpiresAt(new Date(System.currentTimeMillis() + accessExpirySeconds * 1_000L));
            }
        } else {
            if (refreshExpirySeconds > 0) {
                builder.withExpiresAt(new Date(System.currentTimeMillis() + refreshExpirySeconds * 1_000L));
            }
        }
        builder.withClaim("username", claims.username);
        builder.withArrayClaim("roles", claims.roles);
        builder.withClaim("type", tokenType);
        return builder.sign(algorithm);
    }

    // 토큰을 decode해서 claim을 반환하는 메서드
    public Claims verify(String token, String expectedTokenType) {
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        // 토큰 타입 검증
        String tokenType = decodedJWT.getClaim("type").asString();
        if (!expectedTokenType.equals(tokenType)) {
            throw new BusinessException(ErrorCode.NOT_EXPECTED_TOKEN_TYPE);
        }
        return new Claims(jwtVerifier.verify(token));
    }

    // jwt를 만들거나 검증할 때 필요한 정보를 전달하기 위한 클래스
    static public class Claims{
        String username;
        String[] roles;
        Date iat;
        Date exp;

        private Claims(){}

        Claims(DecodedJWT decodedJWT) {
            Claim username = decodedJWT.getClaim("username");
            if (!username.isNull()) {
                this.username = username.asString();
            }
            Claim roles = decodedJWT.getClaim("roles");
            if (!roles.isNull()) {
                this.roles = roles.asArray(String.class);
            }
            this.iat = decodedJWT.getIssuedAt();
            this.exp = decodedJWT.getExpiresAt();
        }

        public static Claims from(String username, String[] roles) {
            Claims claims = new Claims();
            claims.username = username;
            claims.roles = roles;
            return claims;
        }

        public Map<String, Object> asMap() {
            Map<String, Object> map = new HashMap<>();
            map.put("username", username);
            map.put("roles", roles);
            map.put("iat", iat());
            map.put("exp", exp());
            return map;
        }

        private long iat() {
            return iat != null ? iat.getTime() : -1;
        }

        private long exp() {
            return exp != null ? exp.getTime() : -1;
        }
    }
}
