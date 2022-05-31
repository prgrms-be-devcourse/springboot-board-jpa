package org.prgrms.kdt.configuration;

import static java.text.MessageFormat.format;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.prgrms.kdt.dto.UserDto.CurrentUser;
import org.prgrms.kdt.mapper.UserMapper;
import org.prgrms.kdt.repository.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

  private final UserMapper mapper;
  private final UserRepository userRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    String userId = request.getHeader("Authorization");

    if (userId != null) {
      CurrentUser currentUser = userRepository.findById(Long.parseLong(userId))
          .map(mapper::of)
          .orElseThrow(() -> new EntityNotFoundException(format("ID: {0}의 유저를 찾을 수 없습니다", userId)));
      request.getServletContext().setAttribute("currentUser", currentUser);
    }

    return HandlerInterceptor.super.preHandle(request, response, handler);
  }
}