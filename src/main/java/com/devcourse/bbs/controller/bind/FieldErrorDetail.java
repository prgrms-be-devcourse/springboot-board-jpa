package com.devcourse.bbs.controller.bind;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldErrorDetail {
    private String rejectedField;
    private String cause;
}
