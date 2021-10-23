package com.prgrms.board.customValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<Name, String> {

    @Override
    public void initialize(Name name){
    }

    @Override
    public boolean isValid(String field, ConstraintValidatorContext cxt){
        return field != null && field.matches("^[가-힣a-zA-Z]*$");
    }
}
