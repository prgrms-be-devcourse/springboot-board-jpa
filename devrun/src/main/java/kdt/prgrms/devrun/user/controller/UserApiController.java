package kdt.prgrms.devrun.user.controller;

import kdt.prgrms.devrun.common.dto.ApiResult;
import kdt.prgrms.devrun.user.dto.AddUserRequestDto;
import kdt.prgrms.devrun.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/api/v1/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping()
    public ApiResult<Object> create(@Valid @RequestBody AddUserRequestDto addUserRequestDto) {
        final Long savedUserId = userService.createUser(addUserRequestDto);
        return ApiResult.ok(savedUserId);
    }

}
