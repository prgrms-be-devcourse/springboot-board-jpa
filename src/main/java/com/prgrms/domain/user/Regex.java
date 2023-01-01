package com.prgrms.domain.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Regex {

    EMAIL_REGEX("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"),
    PASSWORD_REGEX("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    private final String rgx;

    Regex(String rgx) {
        this.rgx = rgx;
    }
    
    public boolean match(String subject) {
        Pattern p = Pattern.compile(rgx);
        Matcher m = p.matcher(subject);
        return m.matches();
    }
}
