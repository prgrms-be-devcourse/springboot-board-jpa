package org.programmers.board.controller;

import org.programmers.board.dto.MemberCreateRequest;
import org.programmers.board.dto.MemberCreateResponse;
import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Name;
import org.programmers.board.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<MemberCreateResponse> createMember(@RequestBody MemberCreateRequest memberCreateRequest) {
        User user = new User(
                new Name(memberCreateRequest.getName()),
                memberCreateRequest.getAge(),
                memberCreateRequest.getHobby());

        userService.createUser(user);

        MemberCreateResponse response = new MemberCreateResponse(
                user.getName().getName(),
                user.getAge(),
                user.getHobby());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> removeUser(Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}