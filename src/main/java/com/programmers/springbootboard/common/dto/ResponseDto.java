package com.programmers.springbootboard.common.dto;

import lombok.NonNull;
import org.springframework.hateoas.EntityModel;

import java.time.LocalDateTime;

public class ResponseDto<T> {
   // @NonNull
    private String message;
   // @NonNull
    private LocalDateTime serverDateTime;
  //  @NonNull
    private T data;
//    @NonNull
    private T link; // TODO 옵션얼하게 추가할 수 있도록 변경

    /*
    private ResponseDto(ResponseMessage message) {
        this.status = message.getStatus().value();
        this.message = message.name();
    }

    private ResponseDto(ResponseMessage message, T data) {
        this(message);
        this.data = data;
    }

    */


    /*
    public static ResponseDto of(ResponseMessage message) {
        return new ResponseDto(message);
    }

    /*
    public static <T> ResponseDto of(ResponseMessage message, T data) {
        return new ResponseDto(message, data);
    }
         */

    private ResponseDto(ResponseMessage message, T data, T link) {
        this.message = message.name();
        this.serverDateTime = LocalDateTime.now();
        this.data = data;
        this.link = link;
    }

    public static <T> ResponseDto of(ResponseMessage message, T data, T link) {
        return new ResponseDto(message, data, link);
    }

    public static <T> ResponseDto of(ResponseMessage message, EntityModel<T> entityModel) {
        return new ResponseDto(message, entityModel.getContent(), entityModel.getLinks());
    }



    private ResponseDto(ResponseMessage message, T data) {
        this.message = message.name();
        this.serverDateTime = LocalDateTime.now();
        this.data = data;
    }

    // 로그인
    public static <T> ResponseDto of(ResponseMessage message, T data) {
        return new ResponseDto(message, data);
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getServerDateTime() {
        return serverDateTime;
    }

    public T getData() {
        return data;
    }

    public T getLink() {
        return link;
    }
}
