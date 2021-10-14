package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.UserRequestDto;
import com.devco.jpaproject.controller.dto.UserResponseDto;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ApiResponse<Long> insert(@RequestBody UserRequestDto dto){
        Long id = userService.insert(dto);

        return ApiResponse.ok(id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteOne(id);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ApiResponse<UserResponseDto> findById(@PathVariable Long id) throws UserNotFoundException {
        var userResponseDto = userService.findById(id);

        return ApiResponse.ok(userResponseDto);
    }

//    TODO: URI 오류 수정
//    @GetMapping("/user/{name}")
//    public ApiResponse<UserResponseDto> findByName(@PathVariable String name) throws UserNotFoundException {
//        var userResponseDto = userService.findByName(name);
//
//        return ApiResponse.ok(userResponseDto);
//    }
}
