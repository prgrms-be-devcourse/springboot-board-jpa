package com.kdt.springbootboardjpa.domain.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

class PostUpdateRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setup(){
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void notNullValidationTest(){
        var updateRequest = new PostUpdateRequest("", "--");

        Set<ConstraintViolation<PostUpdateRequest>> violations = validator.validate(updateRequest);
        assertThat(violations, hasSize(1));
    }

}