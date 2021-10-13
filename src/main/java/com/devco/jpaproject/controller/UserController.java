package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.UserRequestDto;
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

    @GetMapping("/user")
    public ApiResponse<Long> insert(@RequestBody UserRequestDto dto){
        Long id = userService.insert(dto);

        return ApiResponse.ok(id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteOne(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteOne(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
