package prgrms.project.post.controller.v1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import prgrms.project.post.controller.response.DefaultApiResponse;

import java.util.NoSuchElementException;

import static prgrms.project.post.controller.response.ErrorType.*;

@Slf4j
@RestControllerAdvice(basePackages = "prgrms.project.post.controller")
public class DefaultRestAdvice {

    @ExceptionHandler(RuntimeException.class)
    public DefaultApiResponse<String> catchNotFound(RuntimeException e) {
        log.error("Got unknown server error: {}", e.getMessage());

        return DefaultApiResponse.fail(SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public DefaultApiResponse<String> catchBadRequest(IllegalArgumentException e) {
        log.error("Got bad request: {}", e.getMessage());

        return DefaultApiResponse.fail(INVALID_VALUE_TYPE);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public DefaultApiResponse<String> catchNotFound(NoSuchElementException e) {
        log.error("Got not found: {}", e.getMessage());

        return DefaultApiResponse.fail(ENTITY_NOT_FOUND);
    }
}
