package com.example.springbootboardjpa.enums;

public enum Category {
    INTRODUCE("자기소개"),
    NOTIFICATION("공지사항"),
    NEWS("학과소식"),
    INCRUIT("채용공고");

    private final String contentType;

    Category(String content) {
        this.contentType = content;
    }
}
