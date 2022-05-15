package prgrms.project.post.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import prgrms.project.post.controller.response.DefaultApiResponse;
import prgrms.project.post.service.DefaultPage;
import prgrms.project.post.service.user.UserDto;
import prgrms.project.post.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;

    @PostMapping
    public DefaultApiResponse<Long> registerUser(@RequestBody @Validated UserDto userDto) {
        return DefaultApiResponse.ok(userService.registerUser(userDto));
    }

    @GetMapping("/{userId}")
    public DefaultApiResponse<UserDto> searchUser(@PathVariable Long userId) {
        return DefaultApiResponse.ok(userService.searchById(userId));
    }

    @GetMapping
    public DefaultApiResponse<DefaultPage<UserDto>> searchAllUser(Pageable pageable) {
        return DefaultApiResponse.ok(userService.searchAll(pageable));
    }

    @PutMapping("/{userId}")
    public DefaultApiResponse<Long> updateUser(@PathVariable Long userId, @RequestBody @Validated UserDto userDto) {
        return DefaultApiResponse.ok(userService.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public DefaultApiResponse<Boolean> deleteUser(@PathVariable Long userId) {
        userService.deleteById(userId);

        return DefaultApiResponse.ok(true);
    }
}
