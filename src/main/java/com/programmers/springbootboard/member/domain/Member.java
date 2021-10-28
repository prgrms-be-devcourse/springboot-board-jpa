package com.programmers.springbootboard.member.domain;

import com.programmers.springbootboard.common.domain.BaseEntity;
import com.programmers.springbootboard.member.domain.vo.*;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity<Long> {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Name name;

    @Embedded
    private Age age;

    @Embedded
    private Hobby hobby;

    @Embedded
    private Posts posts;

    public void update(Name name, Age age, Hobby hobby) {
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        lastModifiedId(this.getId());
    }
}
