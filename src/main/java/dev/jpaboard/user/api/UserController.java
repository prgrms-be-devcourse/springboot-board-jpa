package dev.jpaboard.user.api;

import dev.jpaboard.user.dto.response.UserResponse;
import dev.jpaboard.user.application.UserService;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse signUp(@RequestBody UserCreateRequest request) {
    return userService.signUp(request);
  }

  @PostMapping("/users/login")
  @ResponseStatus(HttpStatus.OK)
  public Long login(@RequestBody UserLoginRequest request, HttpServletRequest httpRequest) {
    httpRequest.getSession().invalidate();
    HttpSession session = httpRequest.getSession(true);

    Long userId = userService.login(request);
    session.setAttribute("userId", userId);
    session.setMaxInactiveInterval(3600);
    return userId;
  }

  @GetMapping("/user/logout")
  @ResponseStatus(HttpStatus.OK)
  public String logout(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    if (session != null) {
      session.invalidate();
    }
    return "redirect:/api/users";
  }

  @PatchMapping("/users/update")
  @ResponseStatus(HttpStatus.OK)
  public UserResponse update(UserUpdateRequest userUpdateRequest, HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    return userService.update(userUpdateRequest, (Long) session.getAttribute("userId"));
  }

  @GetMapping("/users/me")
  public UserResponse findUser(HttpServletRequest httpRequest) {
    HttpSession session = httpRequest.getSession(false);
    Long id = (Long) session.getAttribute("userId");
    return userService.findUser(id);
  }

  @DeleteMapping("/users/{id}")
  public void delete(@PathVariable Long id) {
    userService.delete(id);
  }

}
