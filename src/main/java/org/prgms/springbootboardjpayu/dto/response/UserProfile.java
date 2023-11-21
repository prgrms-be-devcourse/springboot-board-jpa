package org.prgms.springbootboardjpayu.dto.response;

import lombok.Builder;

@Builder
public record UserProfile(Long id, String name) {
}
