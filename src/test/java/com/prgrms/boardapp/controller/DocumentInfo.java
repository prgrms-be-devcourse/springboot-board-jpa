package com.prgrms.boardapp.controller;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.List;

import static com.prgrms.boardapp.controller.PageableInfo.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public enum DocumentInfo {
    ID("id", "ID"),
    POST_ID("postId", "게시글 ID"),
    TITLE("title", "제목"),
    CONTENT("content", "내용"),
    CREATED_AT("createdAt", "생성 일자"),
    USER_RESPONSE("user", "유저 정보"),
    USER_RESPONSE_ID("user.id", "유저 ID"),
    USER_RESPONSE_NAME("user.name", "이름"),
    USER_RESPONSE_AGE("user.age", "나이"),
    USER_RESPONSE_HOBBY("user.hobby", "취미"),
    POST_RESPONSE_ARRAY("postDtoList[]", "게시글 리스트"),
    CONTENT_ARRAY("content[]", "조회 결과 리스트");

    private final String field;
    private final String description;

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

    public static List<FieldDescriptor> getPostRequestFieldDescriptors() {
        return List.of(
                fieldWithPath(TITLE.getField()).type(JsonFieldType.STRING).description(TITLE.getDescription()),
                fieldWithPath(CONTENT.getField()).type(JsonFieldType.STRING).description(CONTENT.getDescription())
        );
    }

    public static List<FieldDescriptor> getPostResponseFieldDescriptors() {
        return List.of(
                fieldWithPath(ID.getField()).type(JsonFieldType.NUMBER).description(ID.getDescription()),
                fieldWithPath(TITLE.getField()).type(JsonFieldType.STRING).description(TITLE.getDescription()),
                fieldWithPath(CONTENT.getField()).type(JsonFieldType.STRING).description(CONTENT.getDescription()),
                fieldWithPath(USER_RESPONSE.getField()).type(JsonFieldType.OBJECT).description(USER_RESPONSE.getDescription()),
                fieldWithPath(USER_RESPONSE_ID.getField()).type(JsonFieldType.NUMBER).description(USER_RESPONSE_ID.getDescription()),
                fieldWithPath(USER_RESPONSE_NAME.getField()).type(JsonFieldType.STRING).description(USER_RESPONSE_NAME.getDescription()),
                fieldWithPath(USER_RESPONSE_AGE.getField()).type(JsonFieldType.NUMBER).description(USER_RESPONSE_AGE.getDescription()),
                fieldWithPath(USER_RESPONSE_HOBBY.getField()).type(JsonFieldType.STRING).description(USER_RESPONSE_HOBBY.getDescription())
        );
    }

    public static List<FieldDescriptor> getPageableDescriptors() {
        return List.of(
                fieldWithPath(PAGEABLE.getField()).type(JsonFieldType.STRING).description(PAGEABLE.getDescription()),
                fieldWithPath(LAST.getField()).type(JsonFieldType.BOOLEAN).description(LAST.getDescription()),
                fieldWithPath(TOTAL_PAGES.getField()).type(JsonFieldType.NUMBER).description(TOTAL_PAGES.getDescription()),
                fieldWithPath(TOTAL_ELEMENTS.getField()).type(JsonFieldType.NUMBER).description(TOTAL_ELEMENTS.getDescription()),
                fieldWithPath(SIZE.getField()).type(JsonFieldType.NUMBER).description(SIZE.getDescription()),
                fieldWithPath(SORT.getField()).type(JsonFieldType.OBJECT).description(SORT.getDescription()),
                fieldWithPath(SORT_EMPTY.getField()).type(JsonFieldType.BOOLEAN).description(SORT_EMPTY.getDescription()),
                fieldWithPath(SORT_SORTED.getField()).type(JsonFieldType.BOOLEAN).description(SORT_SORTED.getDescription()),
                fieldWithPath(SORT_UNSORTED.getField()).type(JsonFieldType.BOOLEAN).description(SORT_UNSORTED.getDescription()),
                fieldWithPath(NUMBER.getField()).type(JsonFieldType.NUMBER).description(NUMBER.getDescription()),
                fieldWithPath(FIRST.getField()).type(JsonFieldType.BOOLEAN).description(FIRST.getDescription()),
                fieldWithPath(NUM_OF_ELEMENTS.getField()).type(JsonFieldType.NUMBER).description(NUM_OF_ELEMENTS.getDescription()),
                fieldWithPath(EMPTY.getField()).type(JsonFieldType.BOOLEAN).description(EMPTY.getDescription())
        );
    }
}
