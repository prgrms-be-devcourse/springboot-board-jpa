package com.prgrms.board.util;

import com.prgrms.board.domain.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Optional;

import static com.prgrms.board.dto.request.MemberCreateDto.MEMBER_NAME_MAX_LENGTH;
import static com.prgrms.board.dto.request.MemberCreateDto.MEMBER_NAME_MIN_LENGTH;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberValidator {
    public static void validateMemberName(String name) {
        Assert.hasText(name, "{exception.member.name.null}");

        if (MEMBER_NAME_MAX_LENGTH< name.length() || name.length() < MEMBER_NAME_MIN_LENGTH ) {
            throw new IllegalArgumentException("exception.member.name.length");
        }
    }

    public static void validateMemberAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("exception.member.age.positive");
        }
    }
}
