package kdt.springbootboardjpa.global.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiError(String errorMsg, String uriRequested, String timestamp) {

    public ApiError(String errorMsg, String uriRequested) {
        this(errorMsg, uriRequested, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
