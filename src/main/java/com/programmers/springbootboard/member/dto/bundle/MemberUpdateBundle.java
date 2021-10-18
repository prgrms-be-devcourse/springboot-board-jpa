package com.programmers.springbootboard.member.dto.bundle;

import com.programmers.springbootboard.annotation.ThreadSafety;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import lombok.Builder;

@ThreadSafety
@Builder
public class MemberUpdateBundle {
    private final Long id;
    private final Name name;
    private final Age age;
    private final Hobby hobby;

    public Long getId() {
        return id;
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
