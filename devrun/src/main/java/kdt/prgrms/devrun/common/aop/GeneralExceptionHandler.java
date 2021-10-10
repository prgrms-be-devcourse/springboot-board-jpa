package kdt.prgrms.devrun.common.aop;

import kdt.prgrms.devrun.common.dto.ApiResult;
import kdt.prgrms.devrun.common.enums.ErrorInfo;
import kdt.prgrms.devrun.common.exception.BusinessException;
import kdt.prgrms.devrun.common.exception.PostNotFoundException;
import kdt.prgrms.devrun.common.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        log.error("{}", ex);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
            .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        return ApiResult.error("METHOD_ARG_NOT_VALID", errors);
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResult<?> handleNotFoundException(BusinessException ex){

        log.error("{}", ex);
        final ErrorInfo errorInfo = ex.getErrorInfo();

        return ApiResult.error(errorInfo.getCode(), errorInfo.getMessage());

    }

    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(Exception ex){

        log.error("{}", ex);

        final ErrorInfo errorInfo = ErrorInfo.UNKNOWN;

        return ApiResult.error(errorInfo.getCode(), errorInfo.getMessage());

    }

}
