package com.devcourse.bbs.controller.bind;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUpdateRequest {
    private long id;
    private String title;
    private String content;
}
