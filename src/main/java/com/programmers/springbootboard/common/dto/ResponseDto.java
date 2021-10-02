package kr.ac.hs.oing.common.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private int status;
    private String message;
    private Object data;

    public ResponseDto(ResponseMessage message) {
        this.status = message.status().value();
        this.message = message.name();
    }

    public ResponseDto(ResponseMessage message, Object data) {
        this.status = message.status().value();
        this.message = message.name();
        this.data = data;
    }

    public static ResponseDto of(ResponseMessage message) {
        return new ResponseDto(message);
    }

    public static ResponseDto of(ResponseMessage message, Object data) {
        return new ResponseDto(message, data);
    }

}
