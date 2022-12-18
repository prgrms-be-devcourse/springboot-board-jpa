package kdt.springbootboardjpa.controller.response;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ErrorResponse {
    private String errorMsg;
    private String statusCode;
    private String uriRequested;
    private String timestamp;

    public ErrorResponse(String errorMsg, String statusCode, String uriRequested) {
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
        this.uriRequested = uriRequested;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
