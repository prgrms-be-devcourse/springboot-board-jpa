package com.prgrms.domain.user;

public enum Regex {

    EMAIL_REGEX("[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"),
    PASSWORD_REGEX("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$");

    private final String rgx;

    Regex(String rgx) {
        this.rgx = rgx;
    }

    public String getRgx() {
        return rgx;
    }
}
