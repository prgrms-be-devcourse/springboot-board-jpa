package devcource.hihi.boardjpa.controller;

import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.user.CreateRequestDto;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import devcource.hihi.boardjpa.dto.user.UpdateRequestDto;
import devcource.hihi.boardjpa.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(userService.getUsers(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<ResponseUserDto> createUser(@Valid @RequestBody CreateRequestDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseUserDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping ("/{userId}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long userId, @RequestBody UpdateRequestDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    }