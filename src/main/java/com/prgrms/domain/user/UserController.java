package com.prgrms.domain.user;

import static com.prgrms.dto.UserDto.*;

import com.prgrms.dto.UserDto.Response;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable Long id) {

        Response userById = service.findUserById(id);
        return ResponseEntity.ok(userById);
    }

    @PostMapping
    public ResponseEntity<Void> registerUser(@RequestBody Request userDto) {

        Response response = service.insertUser(userDto);
        return ResponseEntity.created(URI.create("/users/" + response.getUserId())).build();
    }

}
