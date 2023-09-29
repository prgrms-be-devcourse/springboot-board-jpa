package com.blackdog.springbootBoardJpa.domain.user.controller;

import com.blackdog.springbootBoardJpa.domain.user.controller.converter.UserControllerConverter;
import com.blackdog.springbootBoardJpa.domain.user.controller.dto.UserCreateDto;
import com.blackdog.springbootBoardJpa.domain.user.service.UserService;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponse;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponses;
import com.blackdog.springbootBoardJpa.global.response.SuccessResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.blackdog.springbootBoardJpa.global.response.SuccessCode.USER_DELETE_SUCCESS;

@RestController
@RequestMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService service;
    private final UserControllerConverter converter;

    public UserController(
            final UserService service,
            final UserControllerConverter converter
    ) {
        this.service = service;
        this.converter = converter;
    }

    /**
     * register or update user data
     *
     * @param createDto
     * @return ResponseEntity<UserResponse>
     * HttpStatus 201
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> saveUser(@Valid @RequestBody UserCreateDto createDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.saveUser(
                        converter.toRequest(createDto)));
    }

    /**
     * delete user by userId
     *
     * @param userId
     * @return ResponseEntity<SuccessResponse>
     * HttpStatus 200
     */
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<SuccessResponse> deleteUser(@PathVariable long userId) {
        service.deleteUserById(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(SuccessResponse.of(USER_DELETE_SUCCESS));
    }

    /**
     * search all users with pagination
     *
     * @param pageable
     * @return ResponseEntity<UserResponses>
     * HttpStatus 200
     */
    @GetMapping
    public ResponseEntity<UserResponses> getAllUsers(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAllUsers(pageable));
    }

    /**
     * search user by userId
     *
     * @param userId
     * @return ResponseEntity<UserResponse>
     * HttpStatus 200
     */
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findUserById(userId));
    }

}
