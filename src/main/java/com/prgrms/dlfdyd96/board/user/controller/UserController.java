package com.prgrms.dlfdyd96.board.user.controller;

import com.prgrms.dlfdyd96.board.common.api.ApiResponse;
import com.prgrms.dlfdyd96.board.user.dto.CreateUserRequest;
import com.prgrms.dlfdyd96.board.user.dto.UpdateUserRequest;
import com.prgrms.dlfdyd96.board.user.dto.UserResponse;
import com.prgrms.dlfdyd96.board.user.service.UserService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  // TODO: (Refactoring) controller adviser
  @ExceptionHandler
  @ResponseStatus(HttpStatus.NOT_FOUND)
  private ApiResponse<String> notFoundExceptionHandler(NotFoundException exception) {
    return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  private ApiResponse<String> exceptionHandler(Exception exception) {
    return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  public ApiResponse<Long> create(@RequestBody CreateUserRequest createRequestDto) {
    return ApiResponse.ok(userService.save(createRequestDto));
  }

  @GetMapping("/users")
  public ApiResponse<Page<UserResponse>> getAll(Pageable pageable) {
    return ApiResponse.ok(userService.findUsers(pageable));
  }

  @GetMapping("/users/{id}")
  public ApiResponse<UserResponse> getOne(@PathVariable Long id) throws NotFoundException {
    return ApiResponse.ok(userService.findOne(id));
  }

  @PutMapping("/users/{id}")
  public ApiResponse<UserResponse> update(@PathVariable Long id, @RequestBody UpdateUserRequest updateRequestDto)
      throws NotFoundException {
    return ApiResponse.ok(userService.update(id, updateRequestDto));
  }

  @DeleteMapping("/users/{id}")
  public ApiResponse<Integer> delete(@PathVariable Long id) throws NotFoundException {
    userService.delete(id);
    Integer affectedItem = 1;

    return ApiResponse.ok(affectedItem);
  }

}
