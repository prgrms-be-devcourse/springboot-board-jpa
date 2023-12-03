package com.example.board.domain.member.entity;

import com.example.board.domain.role.entity.Role;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRoleId implements Serializable {

    private Member member;
    private Role role;
}
