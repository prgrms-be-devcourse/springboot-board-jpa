package jehs.springbootboardjpa.entity.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jehs.springbootboardjpa.exception.UserErrorMessage;
import jehs.springbootboardjpa.exception.UserException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Age {

    private static final Long MIN_AGE = 0L;

    @Column(name = "age", nullable = false)
    private Long value;

    public Age(Long value) {
        if (value < MIN_AGE) {
            throw new UserException(UserErrorMessage.INVALID_AGE);
        }
        this.value = value;
    }
}
