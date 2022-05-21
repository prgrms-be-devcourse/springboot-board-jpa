package com.prgrms.boardapp.controller;

public enum DocumentInfo {
    ID("id", "ID"),
    POST_ID("postId", "게시글 ID"),
    TITLE("title", "제목"),
    CONTENT("content", "내용"),
    CREATED_AT("createdAt", "생성 일자"),
    USER_DTO("userDto", "유저 정보"),
    USER_DTO_ID("userDto.id", "유저 ID"),
    USER_DTO_NAME("userDto.name", "이름"),
    USER_DTO_AGE("userDto.age", "나이"),
    USER_DTO_HOBBY("userDto.hobby", "취미"),
    CONTENT_ARRAY("content[]", "조회 결과 리스트");

    private final String field;
    private final String description;

    private static final String DOT = ".";

    DocumentInfo(String field, String description) {
        this.field = field;
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public String getDescription() {
        return description;
    }

    public String getFieldPrefixContentArr() {
        return CONTENT_ARRAY.getField() + DOT + this.getField();
    }
}
