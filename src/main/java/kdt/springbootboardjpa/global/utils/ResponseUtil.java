package kdt.springbootboardjpa.global.utils;

import kdt.springbootboardjpa.global.dto.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class ResponseUtil {

    public static <T> ResponseEntity<BaseResponse<T>> ok(HttpHeaders headers, T response){
        BaseResponse<T> baseResponse = BaseResponse.success(HttpStatus.OK, response);
        return new ResponseEntity<>(baseResponse, headers, HttpStatus.OK);
    }

    public static <T> ResponseEntity<BaseResponse<T>> created(T response, URI uri){
        BaseResponse<T> baseResponse = BaseResponse.success(HttpStatus.CREATED, response);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);
        return new ResponseEntity<>(baseResponse, headers, HttpStatus.CREATED);
    }
}
