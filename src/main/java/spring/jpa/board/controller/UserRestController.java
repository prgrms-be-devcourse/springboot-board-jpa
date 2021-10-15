package spring.jpa.board.controller;

import java.util.List;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.jpa.board.dto.user.UserDto;
import spring.jpa.board.service.UserService;

@RestController
public class UserRestController {

  private final UserService userService;

  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  @ExceptionHandler(NotFoundException.class)
  public ApiResponse<String> notFoundHandler(NotFoundException e) {
    return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ApiResponse<String> internalServerErrorHandler(Exception e) {
    return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
  }


  @GetMapping("/users")
  public ApiResponse<List<UserDto>> getAll() {
    return ApiResponse.ok(userService.findAll());
  }

  @GetMapping("/users/{id}")
  public ApiResponse<UserDto> getOne(@PathVariable Long id) throws NotFoundException {
    return ApiResponse.ok(userService.findById(id));
  }

  @PostMapping("/users")
  public ApiResponse<UserDto> save(@RequestBody UserDto userDto) {
    return ApiResponse.ok(userService.save(userDto));
  }


}
