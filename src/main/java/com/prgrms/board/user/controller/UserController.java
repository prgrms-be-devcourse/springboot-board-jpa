package com.prgrms.board.user.controller;

import com.prgrms.board.user.controller.dto.UserControllerConverter;
import com.prgrms.board.user.controller.dto.UserDetailedRequest;
import com.prgrms.board.user.controller.dto.UserShortResponse;
import com.prgrms.board.user.service.UserService;
import com.prgrms.board.user.service.dto.UserDetailedParam;
import com.prgrms.board.user.service.dto.UserShortResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.prgrms.board.user.controller.UserController.USER_REST_URI;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = USER_REST_URI,
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE)
public class UserController {

    public static final String USER_REST_URI = "api/users";

    private final UserService service;
    private final UserControllerConverter converter;

    public UserController(UserService service, UserControllerConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @PostMapping
    public ResponseEntity<UserShortResponse> create(@RequestBody @Validated UserDetailedRequest request) {
        UserDetailedParam param = converter.toUserDetailedParam(request);
        UserShortResult result = service.save(param);
        UserShortResponse response = converter.toUserResponse(result);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
