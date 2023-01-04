package com.prgrms.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regex {

    EMAIL_REGEX("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"),
    PASSWORD_REGEX("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    private final Pattern pattern;

    Regex(String rgx) {

        this.pattern = Pattern.compile(rgx);
    }

    public boolean match(String subject) {

        Matcher m = pattern.matcher(subject);
        return m.matches();
    }

}
