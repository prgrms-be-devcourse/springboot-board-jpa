package jehs.springbootboardjpa.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessage<T> {

    private final String message;
    private T data;

    public SuccessMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public SuccessMessage(String message) {
        this.message = message;
    }
}
