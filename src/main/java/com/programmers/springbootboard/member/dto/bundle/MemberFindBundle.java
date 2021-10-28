package com.programmers.springbootboard.member.dto.bundle;

import lombok.Builder;
import lombok.Getter;

// 메서드가 아니라 엔티티에 따라 dto 긔긔

@Getter
@Builder
public class MemberFindBundle {
    private final Long id;
}
