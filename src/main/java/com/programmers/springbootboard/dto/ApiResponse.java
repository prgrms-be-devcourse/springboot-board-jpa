package com.programmers.springbootboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {

    private String httpMethod;

    private int statusCode;

    private T data;

}
