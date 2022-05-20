package com.su.gesipan.document;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public enum USER_DOC {

    ID(fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")),
    NAME(fieldWithPath("name").type(JsonFieldType.STRING).description("name")),
    AGE(fieldWithPath("age").type(JsonFieldType.NUMBER).description("age")),
    HOBBY(fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")),
    POSTS(fieldWithPath("posts").type(JsonFieldType.ARRAY).description("posts"));
    FieldDescriptor field;
    USER_DOC(FieldDescriptor field) {
    this.field = field;
    }
    FieldDescriptor getField(){
    return field;
    }

    // UserDto.Create
    public static RequestFieldsSnippet createField(){
        return requestFields(
                NAME.getField(),
                AGE.getField().optional(),
                HOBBY.getField().optional()
        );
    }
    // UserDto.Result
    public static ResponseFieldsSnippet resultField(){
        return responseFields(
                ID.getField(),
                NAME.getField(),
                AGE.getField().optional(),
                HOBBY.getField().optional(),
                POSTS.getField().optional()
        );
    }
}
