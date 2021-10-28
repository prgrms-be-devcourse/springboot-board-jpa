package com.programmers.springbootboard.member.dto.bundle;

import com.programmers.springbootboard.member.domain.vo.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignBundle {
    private final Email email;
    private final Name name;
    private final Age age;
    private final Hobby hobby;
}
