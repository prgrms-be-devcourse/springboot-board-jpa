package dev.jpaboard.user.api;

import dev.jpaboard.user.application.UserService;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(OK)
    public void signUp(@RequestBody UserCreateRequest request) {
        userService.signUp(request);
    }

    @PostMapping("/users/login")
    @ResponseStatus(OK)
    public Long login(@RequestBody UserLoginRequest request, HttpServletRequest httpRequest) {
        httpRequest.getSession().invalidate();
        HttpSession session = httpRequest.getSession(true);

        Long userId = userService.login(request);
        session.setAttribute("userId", userId);
        session.setMaxInactiveInterval(3600);
        return userId;
    }

    @GetMapping("/user/logout")
    @ResponseStatus(NO_CONTENT)
    public void logout(HttpServletRequest httpRequest) {
        HttpSession session = httpRequest.getSession(false);
        if (Objects.nonNull(session)) {
            session.invalidate();
        }
    }

    @PatchMapping("/users/update")
    @ResponseStatus(NO_CONTENT)
    public void update(@RequestBody UserUpdateRequest request,
                       @SessionAttribute(name = "userId", required = false) Long userId) {
        userService.update(request, userId);
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
