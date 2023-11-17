package org.prgms.springbootboardjpayu.dto.response;

import lombok.Builder;

public record UserProfile(Long id, String name) {
    @Builder
    public UserProfile(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
