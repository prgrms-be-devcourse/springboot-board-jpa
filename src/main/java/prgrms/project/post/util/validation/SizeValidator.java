package prgrms.project.post.util.validation;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<MySize, String> {

    private int min;
    private int max;

    @Override
    public void initialize(MySize constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.hasText(value) && (value.length() >= min && value.length() <= max);
    }
}
