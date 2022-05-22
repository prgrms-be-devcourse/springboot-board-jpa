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
    USER_DTO("userDto", "유저 정보"),
    USER_DTO_ID("userDto.id", "유저 ID"),
    USER_DTO_NAME("userDto.name", "이름"),
    USER_DTO_AGE("userDto.age", "나이"),
    USER_DTO_HOBBY("userDto.hobby", "취미"),
    USER_DTO_POST_DTO_LIST("userDto.postDtoList", "게시글 리스트"),
    POST_DTO_ARRAY("postDtoList[]", "게시글 리스트"),
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

    public static List<FieldDescriptor> getPostDtoFieldDescriptors() {
        return List.of(
                fieldWithPath(ID.getField()).type(JsonFieldType.NUMBER).description(ID.getDescription()),
                fieldWithPath(TITLE.getField()).type(JsonFieldType.STRING).description(TITLE.getDescription()),
                fieldWithPath(CONTENT.getField()).type(JsonFieldType.STRING).description(CONTENT.getDescription()),
                fieldWithPath(CREATED_AT.getField()).type(JsonFieldType.STRING).description(CREATED_AT.getDescription()),
                fieldWithPath(USER_DTO.getField()).type(JsonFieldType.OBJECT).description(USER_DTO.getDescription()),
                fieldWithPath(USER_DTO_ID.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_ID.getDescription()),
                fieldWithPath(USER_DTO_NAME.getField()).type(JsonFieldType.STRING).description(USER_DTO_NAME.getDescription()),
                fieldWithPath(USER_DTO_AGE.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_AGE.getDescription()),
                fieldWithPath(USER_DTO_HOBBY.getField()).type(JsonFieldType.STRING).description(USER_DTO_HOBBY.getDescription()),
                fieldWithPath(USER_DTO_POST_DTO_LIST.getField()).type(JsonFieldType.ARRAY).description(USER_DTO_POST_DTO_LIST.getDescription())
        );
    }

    public static List<FieldDescriptor> getUserDtoFieldDescriptors() {
        return List.of(
                fieldWithPath(ID.getField()).type(JsonFieldType.NUMBER).description(ID.getDescription()),
                fieldWithPath(TITLE.getField()).type(JsonFieldType.STRING).description(TITLE.getDescription()),
                fieldWithPath(CONTENT.getField()).type(JsonFieldType.STRING).description(CONTENT.getDescription()),
                fieldWithPath(CREATED_AT.getField()).type(JsonFieldType.STRING).description(CREATED_AT.getDescription()),
                fieldWithPath(USER_DTO.getField()).type(JsonFieldType.OBJECT).description(USER_DTO.getDescription()),
                fieldWithPath(USER_DTO_ID.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_ID.getDescription()),
                fieldWithPath(USER_DTO_NAME.getField()).type(JsonFieldType.STRING).description(USER_DTO_NAME.getDescription()),
                fieldWithPath(USER_DTO_AGE.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_AGE.getDescription()),
                fieldWithPath(USER_DTO_HOBBY.getField()).type(JsonFieldType.STRING).description(USER_DTO_HOBBY.getDescription()),
                fieldWithPath(USER_DTO_POST_DTO_LIST.getField()).type(JsonFieldType.ARRAY).description(USER_DTO_POST_DTO_LIST.getDescription())
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
