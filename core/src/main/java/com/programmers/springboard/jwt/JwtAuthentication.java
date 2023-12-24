package com.programmers.springboard.jwt;

import lombok.Getter;

@Getter
public class JwtAuthentication {

    private final String token;

    private final String username;

    public JwtAuthentication(String token, String username) {
        if(token == null) throw new IllegalArgumentException("token must have value");
        if(username == null) throw new IllegalArgumentException("username must have value");

        this.token = token;
        this.username = username;
    }

}
