package com.prgrms.boardjpa.exception;

import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@AllArgsConstructor
public class UnexpectableErrorMessage {
    private static final String messageFormat = "[%s] [%s] [%s]";
    private final HttpServletRequest request;
    private final Exception exception;

    public String getMessage() {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String message = Arrays.toString(exception.getStackTrace());
        return String.format(messageFormat, requestURI, method, message);
    }
}
