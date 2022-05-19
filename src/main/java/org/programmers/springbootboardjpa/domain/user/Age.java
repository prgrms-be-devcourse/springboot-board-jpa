package org.programmers.springbootboardjpa.domain.user;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.springbootboardjpa.domain.user.exception.IllegalBirthDateException;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public final class Age {

    public Age(LocalDate birthDate) {
        if (isNotValid(birthDate)) {
            throw new IllegalBirthDateException(birthDate, "미래 날짜를 생일로 작성할 수 없습니다.");
        }
        this.birthDate = birthDate;
    }

    private boolean isNotValid(LocalDate birthDate) {
        return LocalDate.now().isBefore(birthDate);
    }

    //TODO: 음력 생일 구현
    @Getter
    private LocalDate birthDate;

    public boolean isMinorAge() {
        return (getKoreanYearAge() < 19);
    }

    private Integer getKoreanAge() {
        return LocalDate.now().getYear() - birthDate.getYear() + 1;
    }

    private Integer getKoreanYearAge() {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

    private Integer getInternationalAge() {
        if (isBirthDayPassedInThisYear()) {
            return LocalDate.now().getYear() - birthDate.getYear();
        }
        return LocalDate.now().getYear() - birthDate.getYear() - 1;
    }

    private boolean isBirthDayPassedInThisYear() {
        return LocalDate.now().getDayOfYear() <= birthDate.getDayOfYear();
    }

    @Override
    public String toString() {
        return getInternationalAge().toString();
    }
}