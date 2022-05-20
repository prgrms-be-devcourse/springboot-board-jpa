package com.prgrms.boardapp.utils;

import org.springframework.util.Assert;

public class CommonValidate {
    private static final String NOT_NULL_ERR_MSG = "null 또는 공백은 허용하지 않습니다.";

    private CommonValidate() {}

    public static void validateNotNullString(String name) {
        Assert.notNull(name, NOT_NULL_ERR_MSG);
        Assert.isTrue(!name.trim().isEmpty(), NOT_NULL_ERR_MSG);
    }
}
