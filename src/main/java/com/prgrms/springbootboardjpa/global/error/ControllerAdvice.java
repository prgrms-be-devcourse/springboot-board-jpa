package com.prgrms.springbootboardjpa.global.error;

import com.prgrms.springbootboardjpa.global.error.dto.ErrorReportRequest;
import com.prgrms.springbootboardjpa.global.error.dto.ErrorResponse;
import com.prgrms.springbootboardjpa.post.exception.PostException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler({
            PostException.class,
    })
    public ResponseEntity<ErrorResponse> handleInvalidData(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequestBody() {
        ErrorResponse errorResponse = new ErrorResponse("잘못된 형식의 Request Body 입니다!");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch() {
        ErrorResponse errorResponse = new ErrorResponse("잘못된 데이터 타입입니다!");
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleNotSupportedMethod() {
        ErrorResponse errorResponse = new ErrorResponse("지원하지 않는 HTTP 메소드 요청입니다!");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception e,
                                                                   final HttpServletRequest request) {
        ErrorReportRequest errorReport = new ErrorReportRequest(request, e);
        log.error(errorReport.getLogMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse("예상 하지 못한 서버 에러가 발생하였습니다..");
        return ResponseEntity.internalServerError().body(errorResponse);
    }

}
