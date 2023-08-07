package prgrms.board.user.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prgrms.board.user.application.UserService;
import prgrms.board.user.application.dto.request.UserSaveRequest;
import prgrms.board.user.application.dto.response.UserFindResponse;
import prgrms.board.user.application.dto.response.UserSaveResponse;

@RestController
@RequestMapping("api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserSaveResponse> saveUser(@RequestBody UserSaveRequest request) {
        var response = userService.saveUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserFindResponse> findById(@PathVariable Long id) {
        UserFindResponse response = userService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
