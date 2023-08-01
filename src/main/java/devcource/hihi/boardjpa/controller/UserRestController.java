package devcource.hihi.boardjpa.controller;

import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.user.CreateUserDto;
import devcource.hihi.boardjpa.dto.user.ResponseUserDto;
import devcource.hihi.boardjpa.dto.user.UpdateUserDto;
import devcource.hihi.boardjpa.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(userService.getUsersWithPagination(pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<ResponseUserDto> createUser(@Valid @RequestBody CreateUserDto userDto) {
        return ResponseEntity.ok(userService.createDto(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDto> findByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updatePost(@PathVariable Long id, UpdateUserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    }