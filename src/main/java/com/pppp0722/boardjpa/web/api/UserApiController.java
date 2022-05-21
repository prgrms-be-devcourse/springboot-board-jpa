package com.pppp0722.boardjpa.web.api;

import com.pppp0722.boardjpa.service.user.UserService;
import com.pppp0722.boardjpa.web.dto.UserRequestDto;
import com.pppp0722.boardjpa.web.dto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto receivedUserDto = userService.save(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(receivedUserDto);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto userDto = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @GetMapping("/users")
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable) {
        Page<UserResponseDto> allUserDtos = userService.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(allUserDtos);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id,
        @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userDto = userService.update(id, userRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
