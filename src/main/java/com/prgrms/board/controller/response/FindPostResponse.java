package com.prgrms.board.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FindPostResponse {

    private Long id;
    private String title;
    private String content;
}
