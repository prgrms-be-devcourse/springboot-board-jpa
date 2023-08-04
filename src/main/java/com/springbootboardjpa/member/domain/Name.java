package com.springbootboardjpa.member.domain;

import com.springbootboardjpa.member.exception.InValidMemberException;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Getter
@Slf4j
@Embeddable
public class Name {

    public final static int MAX_LAST_LENGTH = 5;

    private String first;

    private String last;

    public Name(String first, String last) {
        validateFirstName(first);
        this.first = first;
        this.last = last;
    }

    private void validateFirstName(String first) {
        if (first.length() > MAX_LAST_LENGTH) {
            log.info("성을 제외하고 이름은 {}자를 초과할 수 없습니다. 입력된 수 : {}", MAX_LAST_LENGTH, first);
            throw new InValidMemberException(
                    String.format("성을 제외하고 이름은 %d자를 초과할 수 없습니다.", MAX_LAST_LENGTH));
        }
    }
}
