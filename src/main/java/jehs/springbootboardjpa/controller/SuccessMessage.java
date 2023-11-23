package jehs.springbootboardjpa.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessMessage {

    private String message;
    private Object data;

    public SuccessMessage(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public SuccessMessage(String message) {
        this.message = message;
    }
}
