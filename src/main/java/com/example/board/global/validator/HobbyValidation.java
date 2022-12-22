package com.example.board.global.validator;

import com.example.board.domain.hobby.entity.HobbyType;
import org.springframework.util.Assert;

public class HobbyValidation {

    public static void validateHobbyTypeNull(HobbyType hobbyType) {
        Assert.notNull(hobbyType,  "{exception.entity.hobby.type.null}");
    }
}
