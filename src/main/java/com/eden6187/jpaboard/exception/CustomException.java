package com.eden6187.jpaboard.exception;

import com.eden6187.jpaboard.common.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;
}
