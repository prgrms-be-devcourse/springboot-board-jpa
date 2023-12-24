package com.example.boardbatch.batch.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlSupport {

    public static String createUpdatePasswordMailQuery() {
        return "SELECT m FROM Member m WHERE m.lastUpdatedPassword <= :sixMonthsAgo AND m.isDeleted = false";
    }
}
