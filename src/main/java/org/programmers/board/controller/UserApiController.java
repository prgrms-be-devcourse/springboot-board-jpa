package org.programmers.board.controller;

import org.programmers.board.dto.UserDTO;
import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Name;
import org.programmers.board.service.UserService;
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
    public ResponseEntity<?> createNewMember(@RequestBody UserDTO userDTO) {
        User user = new User(new Name(userDTO.getName()), userDTO.getAge(), userDTO.getHobby());

        userService.createUser(user);

        UserDTO response = new UserDTO(
                user.getName().getName(),
                user.getAge(),
                user.getHobby());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<?> removeUser(Long userId) {
        userService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}