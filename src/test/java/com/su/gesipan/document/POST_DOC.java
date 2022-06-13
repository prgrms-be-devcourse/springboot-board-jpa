package com.su.gesipan.document;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestParametersSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

public enum POST_DOC {
    /* POST */
    ID(fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")),
    TITLE(fieldWithPath("title").type(JsonFieldType.STRING).description("title")),
    CONTENT(fieldWithPath("content").type(JsonFieldType.STRING).description("content")),
    USER_ID(fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")),

    /* PAGE */
    PAGE(parameterWithName("page").description("페이지 인덱스 (0부터 시작)").optional()),
    SIZE(parameterWithName("size").description("페이지 단위 (한 페이지에 보여줄 리소스 수)").optional());


    FieldDescriptor field;
    ParameterDescriptor param;

    POST_DOC(FieldDescriptor field) {
        this.field = field;
    }
    POST_DOC(ParameterDescriptor param) {
        this.param = param;
    }

    FieldDescriptor getField() {
        return field;
    }
    ParameterDescriptor getParam() {
        return param;
    }

    /**************************************************************************************************/

    // PostDto.Update
    public static RequestFieldsSnippet update() {
        return requestFields(
                TITLE.getField(),
                CONTENT.getField()
        );
    }
    // PostDto.Create
    public static RequestFieldsSnippet create() {
        return requestFields(
                USER_ID.getField(),
                TITLE.getField(),
                CONTENT.getField()
        );
    }
    // PostDto.Result
    public static ResponseFieldsSnippet result() {
        return responseFields(
                ID.getField(),
                TITLE.getField(),
                CONTENT.getField(),
                USER_ID.getField().optional()
        );
    }
    // Pageable
    public static RequestParametersSnippet pageable() {
        return requestParameters(
                PAGE.getParam(),
                SIZE.getParam()
        );
    }
}
