package com.example.board.domain.hobby.validator;

import org.springframework.util.Assert;

import com.example.board.domain.hobby.entity.HobbyType;

public class HobbyValidator {

    public void validateHobbyTypeNull(HobbyType hobbyType) {
        Assert.notNull(hobbyType,  "{exception.entity.hobby.type.null}");
    }
}
