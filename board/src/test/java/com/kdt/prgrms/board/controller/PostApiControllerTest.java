package com.kdt.prgrms.board.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PostApiControllerTest {

    @Nested
    @DisplayName("addPost메서드는")
    class DescribeAddPost {

        @Nested
        @DisplayName("제목이 없는 게시글 생성요청이 들어오면")
        class ContextWhiteSpaceTitlePostAddRequest {

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() {

            }
        }

        @Nested
        @DisplayName("내용이 없는 게시글 생성요청이 들어오면")
        class ContextWhiteSpaceContentPostAddRequest {

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() {

            }
        }

        @Nested
        @DisplayName("제목과 내용이 있는 게시글 생성요청이 들어오면")
        class ContextPostAddRequest {

            @Test
            @DisplayName("200 ok 응답을 반환한다.")
            void itReturnOk() {

            }

            @Test
            @DisplayName("service의 addPost메서드를 호출한다.")
            void CallServiceAddPost() {

            }
        }
    }
}
