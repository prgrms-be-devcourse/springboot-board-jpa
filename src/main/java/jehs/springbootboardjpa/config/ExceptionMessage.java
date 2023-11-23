package jehs.springbootboardjpa.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ExceptionMessage {

    private final String error;
    private final String message;
}
