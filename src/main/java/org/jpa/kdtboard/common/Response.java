package org.jpa.kdtboard.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created by yunyun on 2021/10/14.
 */

@Getter
@Setter
@NoArgsConstructor
public class Response<T> {
    private int statusCode;
    private T data;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public Response(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(200, data);
    }

    public static <T> Response<T> fail(int statusCode, T errData) {
        return new Response<>(statusCode, errData);
    }
}
