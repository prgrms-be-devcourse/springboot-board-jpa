package com.prgrms.dev.springbootboardjpa.domain.user;

public enum Hobby {
    SPORTS, MOVIE, BOOK;

    public static Hobby getHobby(String hobby) {
        try {
            return Hobby.valueOf(hobby);
        } catch (Exception e) {
            throw new IllegalArgumentException("존재하지 않는 Hobby");
        }
    }
}
