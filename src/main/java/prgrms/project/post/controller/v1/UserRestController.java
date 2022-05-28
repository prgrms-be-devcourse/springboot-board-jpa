package prgrms.project.post.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.post.controller.response.IdResponse;
import prgrms.project.post.controller.response.PageResponse;
import prgrms.project.post.service.user.UserDto;
import prgrms.project.post.service.user.UserService;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<IdResponse> registerUser(@RequestBody @Validated UserDto userDto) {
        return ResponseEntity.ok(IdResponse.of(userService.registerUser(userDto)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> searchUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.searchById(userId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserDto>> searchAllUser(Pageable pageable) {
        return ResponseEntity.ok(userService.searchAll(pageable));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<IdResponse> updateUser(@PathVariable Long userId, @RequestBody @Validated UserDto userDto) {
        return ResponseEntity.ok(IdResponse.of(userService.updateUser(userId, userDto)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);

        return ResponseEntity.ok(Collections.singletonMap("deleted", true));
    }
}
