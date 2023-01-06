package com.prgrms.java.global.model;

import com.prgrms.java.domain.Email;

public record SessionDto(Email email, long userId) {
    public SessionDto(String email, long userId) {
        this(new Email(email), userId);
    }
}
