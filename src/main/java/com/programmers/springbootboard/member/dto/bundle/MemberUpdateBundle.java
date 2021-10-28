package com.programmers.springbootboard.member.dto.bundle;

import com.programmers.springbootboard.annotation.ArbitraryAuthenticationPrincipal;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateBundle {
    @ArbitraryAuthenticationPrincipal
    private final Long id;
    private final Name name;
    private final Age age;
    private final Hobby hobby;
}
