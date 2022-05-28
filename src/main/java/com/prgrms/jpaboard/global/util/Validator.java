package com.prgrms.jpaboard.global.util;

import com.prgrms.jpaboard.global.error.exception.WrongFieldException;
import org.apache.commons.lang3.StringUtils;

public class Validator {
    public static void validateCreatedBy(String className, String createdBy) {
        if(StringUtils.isBlank(createdBy)) {
            throw new WrongFieldException(
                    String.format("%s.createdBy", className), createdBy, "blank is not allowed at createdBy"
            );
        }

        if(createdBy.length() > 255) {
            throw new WrongFieldException(
                    String.format("%s.createdBy", className), createdBy, "createdBy <= 255"
            );
        }
    }
}
