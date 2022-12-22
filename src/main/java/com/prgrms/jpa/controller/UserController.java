package com.prgrms.jpa.controller;

import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.controller.dto.user.CreateUserResponse;
import com.prgrms.jpa.controller.dto.user.GetByIdUserResponse;
import com.prgrms.jpa.service.UserFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> create(@RequestBody @Valid CreateUserRequest createUserRequest) {
        CreateUserResponse id = userFacade.create(createUserRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdUserResponse> getById(@PathVariable long id) {
        GetByIdUserResponse getByIdUserResponse = userFacade.getById(id);
        return ResponseEntity.ok(getByIdUserResponse);
    }
}