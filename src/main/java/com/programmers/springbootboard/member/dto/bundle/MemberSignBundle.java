package com.programmers.springbootboard.member.dto.bundle;

import com.programmers.springbootboard.annotation.ThreadSafety;
import com.programmers.springbootboard.member.domain.vo.*;
import lombok.Builder;

@ThreadSafety
@Builder
public class MemberSignBundle {
    private final Email email;
    private final Name name;
    private final Age age;
    private final Hobby hobby;

    public Email getEmail() {
        return email;
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public Hobby getHobby() {
        return hobby;
    }
}
