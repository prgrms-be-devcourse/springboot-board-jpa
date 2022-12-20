package com.prgrms.hyuk.domain.user;

import static com.prgrms.hyuk.exception.ExceptionMessage.AGE_OUT_OF_RANGE_EXP_MSG;

import com.prgrms.hyuk.exception.AgeOutOfRangeException;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class Age {

    private static final int MIN = 1;
    private static final int MAX = 100;

    private int age;

    protected Age() {
    }

    public Age(int age) {
        validateAgeRange(age);

        this.age = age;
    }

    private void validateAgeRange(int age) {
        if (age < MIN || age > MAX) {
            throw new AgeOutOfRangeException(AGE_OUT_OF_RANGE_EXP_MSG);
        }
    }

    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Age age1 = (Age) o;
        return age == age1.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }
}
