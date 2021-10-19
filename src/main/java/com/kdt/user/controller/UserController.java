package com.kdt.user.controller;

import com.kdt.common.http.ApiResponse;
import com.kdt.user.dto.UserDto;
import com.kdt.user.service.UserService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintDeclarationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    private ApiResponse<String> exceptionHandling(Exception exception){
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ApiResponse.fail(HttpStatus.BAD_REQUEST.value(), errors);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandler(NotFoundException exception){
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @PostMapping("/users")
    public ApiResponse<Long> saveUser(@Valid @RequestBody UserDto userDto) throws NotFoundException{
        Long userId = userService.save(userDto);
        return ApiResponse.ok(userId);
    }

    @PutMapping("/users/{id}")
    public ApiResponse<Long> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) throws NotFoundException{
        Long userId = userService.update(id, userDto);
        return ApiResponse.ok(userId);
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserDto> getUser(@PathVariable Long id) throws NotFoundException{
        UserDto userDto = userService.find(id);
        return ApiResponse.ok(userDto);
    }

    @DeleteMapping("/users/{id}")
    public ApiResponse<Long> deleteUser(@PathVariable Long id) throws NotFoundException {
        userService.delete(id);
        return ApiResponse.ok(id);
    }

}
