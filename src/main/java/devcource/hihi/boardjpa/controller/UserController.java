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

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long id, @RequestBody UpdateRequestDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    }