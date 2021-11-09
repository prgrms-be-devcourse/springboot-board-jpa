package com.example.boardbackend.domain.embeded;
import com.example.boardbackend.common.error.exception.IllegalArgException;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class Email {
    // 검증 후 컬럼으로 지정
    @Column(name = "email", nullable = false, length = 45, unique = true)
    private String address;

    public Email(String address){
        if(!checkAddress(address)){
            throw new IllegalArgException("이메일 형식이 맞지 않습니다!");
        }
        this.address = address;
    }

    public boolean checkAddress(String address) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", address);
    }
}
