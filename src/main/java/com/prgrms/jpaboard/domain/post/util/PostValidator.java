package com.prgrms.jpaboard.domain.post.util;

import com.prgrms.jpaboard.global.error.exception.WrongFieldException;
import org.apache.commons.lang3.StringUtils;

public class PostValidator {
    public static void validateUserId(String className, Long userId) {
        if(userId == null) {
            throw new WrongFieldException(
                    String.format("%s.userId", className), userId, "null is not allowed at userId"
            );
        }
    }

    public static void validateTitle(String className, String title) {
        if(StringUtils.isBlank(title)) {
            throw new WrongFieldException(
                    String.format("%s.title", className), title, "blank is not allowed at title"
            );
        }

        if(title.length() < 1 || title.length() > 100) {
            throw new WrongFieldException(
                    String.format("%s.title", className), title, "1 <= title <= 100"
            );
        }
    }

    public static void validateContent(String className, String content) {
        if(StringUtils.isBlank(content)) {
            throw new WrongFieldException(
                    String.format("%s.content", className), content, "blank is not allowed at content"
            );
        }
    }
}
