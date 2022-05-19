package org.programmers.springbootboardjpa.domain.user;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    public Password(String original) {
        //TODO: 정규식 이용한 패스워드 유효성 검증
        this.hashValue = original.hashCode();
    }

    private int hashValue;
}