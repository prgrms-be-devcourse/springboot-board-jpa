package com.devcourse.springjpaboard.user.controller.stub;

import com.devcourse.springjpaboard.application.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.application.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.application.user.model.User;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class UserStubs {

  private UserStubs() {
  }

  public static CreateUserRequest createUserRequest() {
    return new CreateUserRequest("yongcheol", 11, "coding");
  }

  public static UserResponse userResponse() {
    return new UserResponse(1L, "yongcheol", 11, "coding");
  }

  public static User findUser(Long userId) {
    User user = new User();
    user.setId(1L);
    user.setName("yongcheol");
    user.setAge(22);
    return user;
  }

  public static Stream<Arguments> blankNameUserRequest() {
    return Stream.of(
        Arguments.of("", 20, "test-hobby"),
        Arguments.of(" ", 20, "test-hobby"),
        Arguments.of(null, 20, "test-hobby")
    );
  }

  public static Stream<Arguments> notValidAgeUserRequest() {
    return Stream.of(
        Arguments.of("test", -1, "test-hobby"),
        Arguments.of("test", 201, "test-hobby")
    );
  }

  public static Stream<Arguments> blankHobbyUserRequest() {
    return Stream.of(
        Arguments.of("test", 20, ""),
        Arguments.of("test", 20, " "),
        Arguments.of("test", 20, null)
    );
  }
}
