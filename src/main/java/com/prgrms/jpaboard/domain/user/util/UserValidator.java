package com.prgrms.jpaboard.domain.user.util;

import com.prgrms.jpaboard.global.error.exception.WrongFieldException;
import org.apache.commons.lang3.StringUtils;

public class UserValidator {
    public static void validateName(String className, String name) {
        if(StringUtils.isBlank(name)) {
            throw new WrongFieldException(
                    String.format("%s.name", className), name, "blank is not allowed at name"
            );
        }

        if(name.length() < 1 || name.length() > 50) {
            throw new WrongFieldException(
                    String.format("%s.name", className), name, "1 <= name <= 50"
            );
        }
    }

    public static void validateAge(String className, Integer age) {
        if(age != null &&  age < 1) {
            throw new WrongFieldException(
                    String.format("%s.age ", className), age, "1 <= age"
            );
        }
    }

    public static void validateHobby(String className, String hobby) {
        if(hobby != null) {
            if(StringUtils.isBlank(hobby)) {
                throw new WrongFieldException(
                        String.format("%s.hobby", className), hobby, "blank is not allowed at hobby"
                );
            }

            if(hobby.length() > 255) {
                throw new WrongFieldException(
                        String.format("%s.hobby", className), hobby, "hobby <= 255"
                );
            }
        }
    }
}
