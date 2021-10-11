package yjh.jpa.springnoticeboard.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import javassist.NotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ApiResponse<T> {

    private int statusCode;
    private T data;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;

    public ApiResponse(int statusCode, T data) {
        this.statusCode = statusCode;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    //응답 생성
    public static <T> ApiResponse<T> ok(T data){
        return new ApiResponse<>(200, data);
    }

    public static <T> ApiResponse<T> fail(int statusCode ,T data){
        return new ApiResponse<>(statusCode, data);
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e){
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e){
        return ApiResponse.fail(500, e.getMessage());
    }

}
