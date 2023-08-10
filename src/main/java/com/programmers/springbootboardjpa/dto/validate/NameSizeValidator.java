package com.programmers.springbootboardjpa.dto.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.springframework.beans.BeanWrapperImpl;

public class NameSizeValidator implements ConstraintValidator<NameSize, Object> {

    private String name;

    private static final Pattern IS_STRING_LENGTH_2_TO_20 = Pattern.compile("^.{2,20}$");

    @Override
    public void initialize(NameSize constraintAnnotation) {
        this.name = constraintAnnotation.name();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        String nameValue = (String) new BeanWrapperImpl(object).getPropertyValue(name);
        return IS_STRING_LENGTH_2_TO_20.matcher(nameValue).matches();
    }
}
