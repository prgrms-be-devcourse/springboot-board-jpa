package org.prgrms.board.domain.user.domain;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable
@EqualsAndHashCode
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {
    private static final Pattern passwordPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[\\W]).{8,64})");

    @Column(name = "password", nullable = false)
    private String value;

    public Password(String value) {
        verifyPassword(value);
        this.value = value;
    }

    private void verifyPassword(String value) {
        if(!passwordPattern.matcher(value).matches()){
            throw new IllegalArgumentException("비밀번호는 8자 이상, 특수문자가 최소 1개 이상은 포함되야 합니다.");
        }
    }
}
