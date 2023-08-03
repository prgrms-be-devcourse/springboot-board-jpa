package com.prgrms.board.global.exception;

import static org.springframework.http.HttpStatus.*;

import java.util.Optional;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.prgrms.board.domain.post.exception.PostNotFoundException;
import com.prgrms.board.domain.user.exception.UserNotFoundException;
import com.prgrms.board.global.common.dto.BaseResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler({
        UserNotFoundException.class,
        PostNotFoundException.class,
    })
    public BaseResponse<Object> handleNotFound(CustomException exception) {
        log.error("[NotFound] => ", exception);
        return BaseResponse.error(exception.getError());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<Object> handleBadRequestException(MethodArgumentNotValidException exception) {
        log.error("[BadRequest] => ", exception);

        String errorMessage = Optional.ofNullable(exception.getBindingResult().getFieldError())
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse("입력 값이 올바르지 않습니다.");
        return BaseResponse.error(BAD_REQUEST, errorMessage);
    }
}
