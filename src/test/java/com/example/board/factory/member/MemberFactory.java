package com.example.board.factory.member;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.role.entity.Role;
import com.example.board.domain.role.entity.RoleType;

import java.util.List;

public class MemberFactory {

    public static Member createMemberWithRoleUser() {
        return new Member(
            "test1@gmail.com",
            "test123!",
            "홍길동",
            22,
            "리그오브레전드",
            List.of(new Role(RoleType.USER))
        );
    }
}
