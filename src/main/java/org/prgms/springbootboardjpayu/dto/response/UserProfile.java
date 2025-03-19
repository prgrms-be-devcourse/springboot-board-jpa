package org.prgms.springbootboardjpayu.dto.response;

import lombok.Builder;


public record UserProfile(Long id, String name) {

    @Builder
    public UserProfile {
    }
}
